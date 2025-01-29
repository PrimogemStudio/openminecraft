package com.primogemstudio.engine

import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.DuringSetupAccess
import java.lang.foreign.*
import java.lang.foreign.ValueLayout.*
import java.lang.Class

class NativeImageFeature: Feature {
    override fun duringSetup(access: DuringSetupAccess) {
		val rfaClass = Class.forName("org.graalvm.nativeimage.hosted.RuntimeForeignAccess")
		listOf(
			FunctionDescriptor.of(JAVA_BOOLEAN),
			FunctionDescriptor.of(ADDRESS, ADDRESS),
			FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS),
			FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT),
			FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS, ADDRESS),
			FunctionDescriptor.of(JAVA_INT, ADDRESS, JAVA_INT, ADDRESS),
			FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS),
			FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS),
			FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, JAVA_INT),
			FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS),
			FunctionDescriptor.of(ADDRESS, JAVA_INT),
			FunctionDescriptor.of(JAVA_INT, ADDRESS),
			FunctionDescriptor.of(ADDRESS)
		).forEach {
			rfaClass.getMethod("registerForDowncall", Any::class.java, Array<Any>::class.java).invoke(null, it, arrayOf<Any>())
		}

		listOf(
            FunctionDescriptor.ofVoid(JAVA_INT, ADDRESS),
			FunctionDescriptor.ofVoid(ADDRESS, JAVA_INT, JAVA_INT)
		).forEach { rfaClass.getMethod("registerForUpcall", Any::class.java, Array<Any>::class.java).invoke(null, it, arrayOf<Any>()) }
    }
}
