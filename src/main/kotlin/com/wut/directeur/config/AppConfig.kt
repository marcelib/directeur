package com.wut.directeur.config

import com.wut.directeur.exception.ConfigurationFileNotFoundException
import org.apache.commons.configuration.CompositeConfiguration
import org.apache.commons.configuration.Configuration
import org.apache.commons.configuration.PropertiesConfiguration
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.ObjectUtils
import org.slf4j.LoggerFactory
import java.io.File
import java.math.BigDecimal

class AppConfig {

  companion object {
    private val LOG = LoggerFactory.getLogger(AppConfig::class.java)

    private lateinit var dynamicPropertiesLocation: String

    private lateinit var configuration: Configuration

    private var isInitialized: Boolean = false

    fun setPropertiesLocation(location: String) {
      dynamicPropertiesLocation = location
    }

    fun updateConfig(): Configuration {
      LOG.info("Reading dynamic configuration from file: " + dynamicPropertiesLocation + "\n\n")

      val propertiesFile = File(dynamicPropertiesLocation)
      if (!propertiesFile.exists()) {
        throw ConfigurationFileNotFoundException(propertiesFile)
      }

      val newConfig = CompositeConfiguration()
      newConfig.addConfiguration(PropertiesConfiguration(propertiesFile))

      configuration = newConfig.interpolatedConfiguration()
      val output = StringBuilder("\n*** Dynamic parameters ***\n\n")
      configuration.keys.forEachRemaining { key -> output.append(String.format("[%s] = [%s]\n", key, configuration.getProperty(key))) }

      LOG.info(output.toString())

      return configuration
    }

    @Synchronized
    fun getConfig(): Configuration {
      if (!isInitialized) {
        return updateConfig()
          .also { isInitialized = true }
      }

      return configuration
    }

    fun getBoolean(param: AppParam): Boolean {
      return getConfig().getBoolean(param.key, param.defaultValue as Boolean?)
    }

    fun getInteger(param: AppParam): Int {
      return getConfig().getInteger(param.key, param.defaultValue as Int?)
    }

    fun getLong(param: AppParam): Long {
      return getConfig().getLong(param.key, param.defaultValue as Long?)
    }

    fun getBigDecimal(param: AppParam): BigDecimal? {
      return getConfig().getBigDecimal(param.key, param.defaultValue as BigDecimal?)
    }

    fun getString(param: AppParam): String {
      return getConfig().getString(param.key, param.defaultValue as String?)
    }

    fun getStringArray(param: AppParam): Array<String> {
      return ObjectUtils.defaultIfNull(getConfig().getStringArray(param.key), param.defaultValue) as Array<String>
    }

    fun getStringList(param: AppParam): List<String> {
      return getStringArray(param).toList()
    }

    fun doesStringArrayContain(param: AppParam, value: String): Boolean {
      val values = getStringArray(param)
      return ArrayUtils.contains(values, value)
    }
  }
}
