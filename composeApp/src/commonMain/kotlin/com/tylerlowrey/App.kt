package com.tylerlowrey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

import frc_kotlin_project_generator.composeapp.generated.resources.Res
import frc_kotlin_project_generator.composeapp.generated.resources.generate_project_action
import frc_kotlin_project_generator.composeapp.generated.resources.project_name
import frc_kotlin_project_generator.composeapp.generated.resources.project_path
import frc_kotlin_project_generator.composeapp.generated.resources.project_path_selection_action
import frc_kotlin_project_generator.composeapp.generated.resources.project_path_modify_selection_action
import frc_kotlin_project_generator.composeapp.generated.resources.project_name_placeholder
import io.github.vinceglb.filekit.compose.rememberDirectoryPickerLauncher
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun App() {
    MaterialTheme {
        var projectName by remember { mutableStateOf("")}
        var projectDestinationPath by remember { mutableStateOf("") }
        val projectPathSelectionActionText = stringResource(Res.string.project_path_selection_action)
        val projectPathModifySelectionActionText =
            stringResource(Res.string.project_path_modify_selection_action)
        var projectFolderSelectionButtonText by remember {
            mutableStateOf(projectPathSelectionActionText)
        }
        var generatingText by remember { mutableStateOf("") }
        var isGenerateButtonEnabled by remember { mutableStateOf(false) }
        val directoryPickerLauncher = rememberDirectoryPickerLauncher(
            title = "Pick project destination folder",
        ) {
            projectDestinationPath = it?.path ?: ""
            if (projectDestinationPath != "") {
                isGenerateButtonEnabled = true
                projectFolderSelectionButtonText = projectPathModifySelectionActionText
            }
        }

        Column(
            Modifier.fillMaxWidth()
                .padding(0.dp, 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                label = { Text(stringResource(Res.string.project_name)) },
                value = projectName,
                placeholder = { Text(stringResource(Res.string.project_name_placeholder)) },
                onValueChange = { projectName = it},
            )

            TextField(
                label = { Text(stringResource(Res.string.project_path)) },
                value = projectDestinationPath,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.padding(0.dp, 8.dp),
            )

            Button(
                onClick = {
                    directoryPickerLauncher.launch()
                }
            ) { Text(projectFolderSelectionButtonText) }

            Button(
                onClick = {
                    generatingText = "Generating Project | name=$projectName " +
                            "destination=$projectDestinationPath"
                    generateProject(projectName, projectDestinationPath)
                    isGenerateButtonEnabled = false
                },
                enabled = isGenerateButtonEnabled
            ) {
                Text(stringResource(Res.string.generate_project_action))
            }

            Text(generatingText)
        }
    }
}