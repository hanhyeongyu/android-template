/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.template.settings

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.template.core.datastore.DarkTheme
import com.example.template.core.datastore.DarkTheme.DARK
import com.example.template.core.datastore.DarkTheme.FOLLOW_SYSTEM
import com.example.template.core.datastore.DarkTheme.LIGHT
import com.example.template.core.datastore.ThemeBrand
import com.example.template.core.datastore.ThemeBrand.ANDROID
import com.example.template.core.datastore.ThemeBrand.DEFAULT
import com.example.template.core.designsystem.component.TemplateTextButton
import com.example.template.core.designsystem.theme.TemplateTheme
import com.example.template.core.designsystem.theme.supportsDynamicTheming
import com.example.template.settings.R.string
import com.example.template.settings.SettingsUiState.Loading
import com.example.template.settings.SettingsUiState.Success
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: Settings = hiltViewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsDialog(
        onDismiss = onDismiss,
        settingsUiState =  settingsUiState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@Composable
fun SettingsDialog(
    settingsUiState: SettingsUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkTheme: DarkTheme) -> Unit,
) {
    val configuration = LocalConfiguration.current

    /**
     * usePlatformDefaultWidth = false is use as a temporary fix to allow
     * height recalculation during recomposition. This, however, causes
     * Dialog's to occupy full width in Compact mode. Therefore max width
     * is configured below. This should be removed when there's fix to
     * https://issuetracker.google.com/issues/221643630
     */
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(string.settings_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (settingsUiState) {
                    Loading -> {
                        Text(
                            text = stringResource(string.settings_loading),
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }

                    is Success -> {
                        SettingsPanel(
                            settings = settingsUiState.settings,
                            supportDynamicColor = supportDynamicColor,
                            onChangeThemeBrand = onChangeThemeBrand,
                            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                            onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(top = 8.dp))
                LinksPanel()
            }
            //TrackScreenViewEvent(screenName = "Settings")
        },
        confirmButton = {
            Text(
                text = stringResource(string.settings_dismiss_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
    )
}

// [ColumnScope] is used for using the [ColumnScope.AnimatedVisibility] extension overload composable.
@Composable
private fun ColumnScope.SettingsPanel(
    settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkTheme) -> Unit,
) {
    SettingsDialogSectionTitle(text = stringResource(string.settings_theme))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_brand_default),
            selected = settings.brand == DEFAULT,
            onClick = { onChangeThemeBrand(DEFAULT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_brand_android),
            selected = settings.brand == ANDROID,
            onClick = { onChangeThemeBrand(ANDROID) },
        )
    }
    AnimatedVisibility(visible = settings.brand == DEFAULT && supportDynamicColor) {
        Column {
            SettingsDialogSectionTitle(text = stringResource(string.settings_dynamic_color_preference))
            Column(Modifier.selectableGroup()) {
                SettingsDialogThemeChooserRow(
                    text = stringResource(string.settings_dynamic_color_yes),
                    selected = settings.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(true) },
                )
                SettingsDialogThemeChooserRow(
                    text = stringResource(string.settings_dynamic_color_no),
                    selected = !settings.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(false) },
                )
            }
        }
    }
    SettingsDialogSectionTitle(text = stringResource(string.settings_dark_mode_preference))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_dark_mode_config_system_default),
            selected = settings.darkThemeConfig == FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(FOLLOW_SYSTEM) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_dark_mode_config_light),
            selected = settings.darkThemeConfig == LIGHT,
            onClick = { onChangeDarkThemeConfig(LIGHT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(string.settings_dark_mode_config_dark),
            selected = settings.darkThemeConfig == DARK,
            onClick = { onChangeDarkThemeConfig(DARK) },
        )
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LinksPanel() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        val uriHandler = LocalUriHandler.current
        TemplateTextButton(
            onClick = { uriHandler.openUri(PRIVACY_POLICY_URL) },
        ) {
            Text(text = stringResource(string.settings_privacy_policy))
        }
        val context = LocalContext.current
        TemplateTextButton(
            onClick = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            },
        ) {
            Text(text = stringResource(string.settings_licenses))
        }
        TemplateTextButton(
            onClick = { uriHandler.openUri(BRAND_GUIDELINES_URL) },
        ) {
            Text(text = stringResource(string.settings_brand_guidelines))
        }
        TemplateTextButton(
            onClick = { uriHandler.openUri(FEEDBACK_URL) },
        ) {
            Text(text = stringResource(string.settings_feedback))
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsDialog() {
    TemplateTheme{
        SettingsDialog(
            onDismiss = {},
            settingsUiState = Success(
                UserEditableSettings(
                    brand = DEFAULT,
                    darkThemeConfig = FOLLOW_SYSTEM,
                    useDynamicColor = false,
                ),
            ),
            onChangeThemeBrand = {},
            onChangeDynamicColorPreference = {},
            onChangeDarkThemeConfig = {},
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsDialogLoading() {
    TemplateTheme{
        SettingsDialog(
            onDismiss = {},
            settingsUiState = Loading,
            onChangeThemeBrand = {},
            onChangeDynamicColorPreference = {},
            onChangeDarkThemeConfig = {},
        )
    }
}

private const val PRIVACY_POLICY_URL = "https://policies.google.com/privacy"
private const val BRAND_GUIDELINES_URL = "https://developer.android.com/distribute/marketing-tools/brand-guidelines"
private const val FEEDBACK_URL = "https://goo.gle/nia-app-feedback"