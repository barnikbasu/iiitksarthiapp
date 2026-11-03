package com.example.core.model

data class CampusNotification(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val category: String, // "ACADEMIC", "EXAMINATION", "EVENT", "EMERGENCY", "SYSTEM"
    val isRead: Boolean = false,
    val isImportant: Boolean = false
)

data class AcademicResource(
    val id: String,
    val title: String,
    val category: String, // "PORTAL", "REGULATION", "LIBRARY", "SYLLABUS", "FEES"
    val description: String,
    val url: String,
    val isExternal: Boolean = true
)
