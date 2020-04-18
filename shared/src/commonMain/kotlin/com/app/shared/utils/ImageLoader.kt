package com.app.shared.utils

interface ImageLoader<Target, Resource> {
    fun load(resource: Resource?, into: Target)
}