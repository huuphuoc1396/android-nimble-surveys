package co.nimblehq.surveys.customRules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config.Companion.EXCLUDES_KEY
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.internal.valueOrDefaultCommaSeparated
import io.gitlab.arturbosch.detekt.rules.identifierName
import org.jetbrains.kotlin.psi.KtNamedFunction

class IgnoreComposableFunctionRule : Rule() {
    override val issue: Issue
        get() = Issue(javaClass.simpleName, Severity.Style, "", Debt.FIVE_MINS)

    private val excludes = valueOrDefaultCommaSeparated(EXCLUDES_KEY, emptyList())

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)
        // Get the file path of the function
        val filePath = function.containingKtFile.virtualFilePath
        // Check if the file path matches any of the patterns in the excludes field
        val isExcluded = excludes.any { filePath.contains(Regex(it)) }
        // If the file is excluded, return early and do not perform any checks
        if (isExcluded) {
            return
        }
        val isComposable = function.annotationEntries.any { it.text == "@Composable" }
        if (isComposable) {
            // Ignore the function
            return
        }
        // Check the function name here
        // If it does not match the pattern, report it
        if (!function.identifierName().matches(Regex("[a-z][a-zA-Z0-9]*"))) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(function),
                    "Function name does not match the pattern"
                )
            )
        }
    }
}