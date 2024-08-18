package com.tylerlowrey

import java.io.File

actual fun generateProject(projectName: String, projectDestinationPath: String) {
    val projectPathWithProjectName = "$projectDestinationPath/$projectName"
    val templateRepoUrl = "https://github.com/tylerlowrey/frc-kotlin-logged-robot-template"

    val gitCloneProcessBuilder = ProcessBuilder("git", "clone", templateRepoUrl, projectPathWithProjectName)

    val removeGitDirectoryProcessBuilder = ProcessBuilder("rm", "-rf", "$projectPathWithProjectName/.git")
    removeGitDirectoryProcessBuilder.directory(File(projectPathWithProjectName))

    val gitAddProcessBuilder = ProcessBuilder("git", "add", ".")
    removeGitDirectoryProcessBuilder.directory(File(projectPathWithProjectName))

    val gitCommitProcessBuilder = ProcessBuilder("git", "commit", "-m", "Initial commit")
    removeGitDirectoryProcessBuilder.directory(File(projectPathWithProjectName))

    println("Cloning git repo...")
    var exitCode = gitCloneProcessBuilder.waitForCompletion()
    println("Finished cloning git repo with exitCode=$exitCode")

    println("Removing git directory...")
    exitCode = removeGitDirectoryProcessBuilder.waitForCompletion()
    println("Finished removing git directory with exitCode=$exitCode")

    println("Adding files to git commit...")
    exitCode = gitAddProcessBuilder.waitForCompletion()
    println("Finished adding files to commit with exitCode=$exitCode")

    println("Creating initial commit...")
    exitCode = gitCommitProcessBuilder.waitForCompletion()
    println("Finished creating initial commit with exitCode=$exitCode")
}

typealias ProcessExitCode = Int

private fun ProcessBuilder.waitForCompletion(): ProcessExitCode {
    return start().waitFor()
}
