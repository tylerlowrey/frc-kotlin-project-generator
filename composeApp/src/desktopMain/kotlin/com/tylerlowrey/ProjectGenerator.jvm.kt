package com.tylerlowrey

import java.io.File
import kotlin.streams.asSequence

actual fun generateProject(projectName: String, projectDestinationPath: String) {
    val projectPathWithProjectName = "$projectDestinationPath/$projectName"
    val templateRepoUrl = "https://github.com/tylerlowrey/frc-kotlin-logged-robot-template"

    try {
        if (isGitUserNameAndEmailConfigured()) {
            println("ERROR: git user.name or user.email is not configured")
            return
        }

        val gitCloneProcessBuilder = ProcessBuilder("git", "clone", templateRepoUrl, projectPathWithProjectName)

        val removeGitDirectoryProcessBuilder = ProcessBuilder("rm", "-rf", "$projectPathWithProjectName/.git")
        removeGitDirectoryProcessBuilder.directory(File(projectPathWithProjectName))

        val gitInitProcessBuilder = ProcessBuilder("git", "init")
        gitInitProcessBuilder.directory(File(projectPathWithProjectName))

        val gitAddProcessBuilder = ProcessBuilder("git", "add", ".")
        gitAddProcessBuilder.directory(File(projectPathWithProjectName))

        val gitCommitProcessBuilder = ProcessBuilder("git", "commit", "-m", "Initial commit")
        gitCommitProcessBuilder.directory(File(projectPathWithProjectName))

        println("Cloning git repo...")
        var exitCode = gitCloneProcessBuilder.waitForCompletion()
        println("Finished cloning git repo with exitCode=$exitCode")

        println("Removing git directory...")
        exitCode = removeGitDirectoryProcessBuilder.waitForCompletion()
        println("Finished removing git directory with exitCode=$exitCode")

        println("Initializing git for project...")
        exitCode = gitInitProcessBuilder.waitForCompletion()
        println("Finished initializing git with exitCode=$exitCode")

        println("Adding files to git commit...")
        exitCode = gitAddProcessBuilder.waitForCompletion()
        println("Finished adding files to commit with exitCode=$exitCode")

        println("Creating initial commit...")
        exitCode = gitCommitProcessBuilder.waitForCompletion()
        println("Finished creating initial commit with exitCode=$exitCode")
    } catch (e: Exception) {
        println("ERROR: Unable to complete project generation")
        e.printStackTrace()
    }

}

typealias ProcessExitCode = Int

private fun ProcessBuilder.waitForCompletion(): ProcessExitCode {
    return start().waitFor()
}

private fun isGitUserNameAndEmailConfigured(): Boolean {
    var userName = ""
    var userEmail = ""
    val gitConfigListProcessBuilder = ProcessBuilder("git", "config", "--list")
    val process = gitConfigListProcessBuilder.start()
    process.inputStream.bufferedReader()
        .lines()
        .asSequence()
        .forEach {
            if (it.contains("user.name=")) {
                val parts = it.split("=")
                if (parts.size == 2) {
                    userName = parts
                        .last()
                }
            } else if (it.contains("user.email=")) {
                val parts = it.split("=")
                if (parts.size == 2) {
                    userEmail = parts.last()
                }
            }
        }

    return userName.isNotEmpty() && userEmail.isNotEmpty()
}
