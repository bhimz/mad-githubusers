package com.bhimz.githubusers.data.mapper

import com.bhimz.githubusers.data.network.RepoResponse
import com.bhimz.githubusers.domain.Repo

val RepoResponse.repo: Repo
    get() = Repo(id, name, description, language)