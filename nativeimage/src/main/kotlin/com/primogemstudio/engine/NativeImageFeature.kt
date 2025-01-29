package com.primogemstudio.engine

import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.DuringSetupAccess
import java.lang.foreign.*
import java.lang.foreign.ValueLayout.*
import java.lang.foreign.MemoryLayout
import java.lang.Class

class NativeImageFeature: Feature {
	override fun duringSetup(access: DuringSetupAccess) {
		val rfaClass = Class.forName("org.graalvm.nativeimage.hosted.RuntimeForeignAccess")
		val regDowncall = rfaClass.getMethod("registerForDowncall", Any::class.java, Array<Any>::class.java)
		val regUpcall = rfaClass.getMethod("registerForUpcall", Any::class.java, Array<Any>::class.java)

		val basicTypes = listOf(JAVA_BOOLEAN, JAVA_LONG, JAVA_INT, JAVA_FLOAT, JAVA_DOUBLE, JAVA_BYTE, JAVA_SHORT, ADDRESS)
		val downcallTypes = mutableListOf<FunctionDescriptor>()
		val upcallTypes = mutableListOf<FunctionDescriptor>()
		for (ret in basicTypes) {
			downcallTypes.add(FunctionDescriptor.of(ret))
			for (arg1 in basicTypes) {
				downcallTypes.add(FunctionDescriptor.of(ret, arg1))
				for (arg2 in basicTypes) {
					downcallTypes.add(FunctionDescriptor.of(ret, arg1, arg2))
					for (arg3 in basicTypes) {
						downcallTypes.add(FunctionDescriptor.of(ret, arg1, arg2, arg3))
						for (arg4 in basicTypes) {
							downcallTypes.add(FunctionDescriptor.of(ret, arg1, arg2, arg3, arg4))
							for (arg5 in basicTypes) {
								downcallTypes.add(FunctionDescriptor.of(ret, arg1, arg2, arg3, arg4, arg5))
							}
						}
					}
				}
			}
		}

		upcallTypes.add(FunctionDescriptor.ofVoid())
		for (arg1 in basicTypes) {
			upcallTypes.add(FunctionDescriptor.ofVoid(arg1))
			for (arg2 in basicTypes) {
				upcallTypes.add(FunctionDescriptor.ofVoid(arg1, arg2))
				for (arg3 in basicTypes) {
					upcallTypes.add(FunctionDescriptor.ofVoid(arg1, arg2, arg3))
					for (arg4 in basicTypes) {
						upcallTypes.add(FunctionDescriptor.ofVoid(arg1, arg2, arg3, arg4))
						for (arg5 in basicTypes) {
							upcallTypes.add(FunctionDescriptor.ofVoid(arg1, arg2, arg3, arg4, arg5))
							for (arg6 in basicTypes) {
								for (arg7 in basicTypes) {
									upcallTypes.add(FunctionDescriptor.ofVoid(arg1, arg2, arg3, arg4, arg5, arg6, arg7))
								}
							}
						}
					}
				}
			}
		}
		// For harfbuzz paint api
		upcallTypes.add(FunctionDescriptor.ofVoid(ADDRESS, ADDRESS, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, ADDRESS))
		upcallTypes.add(FunctionDescriptor.ofVoid(ADDRESS, ADDRESS, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, ADDRESS))
		upcallTypes.add(FunctionDescriptor.ofVoid(ADDRESS, ADDRESS, ADDRESS, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_FLOAT, ADDRESS, ADDRESS))
		upcallTypes.add(FunctionDescriptor.ofVoid(ADDRESS, ADDRESS, ADDRESS, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, ADDRESS))
		upcallTypes.add(FunctionDescriptor.ofVoid(ADDRESS, ADDRESS, ADDRESS, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, ADDRESS))

		downcallTypes.forEach {
			regDowncall.invoke(null, it, arrayOf<Any>())
		}

		upcallTypes.forEach {
			regUpcall.invoke(null, it, arrayOf<Any>())
		}
	}
}
