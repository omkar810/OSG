package com.example.omshivgoraksha.data.local

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

object FileUtils {

    fun uriToFile(
        context: Context,
        uri: Uri
    ): File? {

        return try {

            val fileName =
                getFileName(
                    context,
                    uri
                ) ?: "document_${System.currentTimeMillis()}"

            val file =
                File(
                    context.cacheDir,
                    fileName
                )

            context.contentResolver
                .openInputStream(uri)
                ?.use { input ->

                    file.outputStream()
                        .use { output ->

                            input.copyTo(output)
                        }
                }

            file

        } catch (e: Exception) {

            null
        }
    }


    private fun getFileName(
        context: Context,
        uri: Uri
    ): String? {

        var fileName: String? = null

        if (uri.scheme == "content") {

            context.contentResolver
                .query(
                    uri,
                    null,
                    null,
                    null,
                    null
                )
                ?.use { cursor ->

                    val nameIndex =
                        cursor.getColumnIndex(
                            OpenableColumns.DISPLAY_NAME
                        )

                    if (
                        nameIndex >= 0 &&
                        cursor.moveToFirst()
                    ) {

                        fileName =
                            cursor.getString(
                                nameIndex
                            )
                    }
                }
        }

        if (fileName == null) {

            fileName =
                uri.path
                    ?.substringAfterLast("/")
        }

        return fileName
    }
}