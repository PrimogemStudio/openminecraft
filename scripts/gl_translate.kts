import java.util.*

val sysin = Scanner(System.`in`)

val types = mapOf(
    Pair("b", "Byte"),
    Pair("c", "Char"),
    Pair("s", "Short"),
    Pair("i", "Int"),
    Pair("f", "Float"),
    Pair("d", "Double"),
    Pair("z", "Boolean"),
    Pair("l", "Long"),
    Pair("z[", "HeapBooleanArray"),
    Pair("b[", "HeapByteArray"),
    Pair("d[", "HeapDoubleArray"),
    Pair("f[", "HeapFloatArray"),
    Pair("i[", "HeapIntArray"),
    Pair("l[", "HeapLongArray"),
    Pair("s[", "HeapShortArray"),
    Pair("*b", "HeapByte"),
    Pair("*c", "HeapChar"),
    Pair("*s", "HeapShort"),
    Pair("*i", "HeapInt"),
    Pair("*f", "HeapFloat"),
    Pair("*d", "HeapDouble"),
    Pair("*z", "HeapBoolean"),
    Pair("*l", "HeapLong"),
    Pair("*", "MemorySegment")
)

fun remap(s: String): String {
    if (s in types) return types[s]!!
    else return s
}

while (true) {
    val args = sysin.nextLine().split(",").map { it.split(".") }

    val funname = args[0][0]
    val typen = remap(args[0][1])
    val ar = args.subList(1, args.size).map { "${it[0]}: ${remap(it[1])}" }.joinToString(", ")
    val call = args.subList(1, args.size).map { it[0] }.joinToString(", ")
    if (typen == "") println("fun $funname(${ar}) = callVoidFunc(\"$funname\", $call)") else println("fun $funname(${ar}): $typen = callFunc(\"$funname\", $typen::class, $call)")
}