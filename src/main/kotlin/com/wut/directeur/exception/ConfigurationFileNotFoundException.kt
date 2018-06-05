package com.wut.directeur.exception

import java.io.File

class ConfigurationFileNotFoundException(file: File) : RuntimeException("File " + file.absolutePath + " not found")
