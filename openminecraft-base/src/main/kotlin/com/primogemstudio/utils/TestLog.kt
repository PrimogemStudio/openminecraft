package com.primogemstudio.utils

import org.apache.logging.log4j.LogManager

class TestLog {
    companion object {
        fun log() {
            val logger = LogManager.getLogger("test")
            logger.info("test!")
        }
    }
}