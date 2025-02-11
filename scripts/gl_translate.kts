import java.io.OutputStreamWriter
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

val sysin = Scanner(System.`in`)
val f = OutputStreamWriter(Files.newOutputStream(Path("temp.txt")))

Runtime.getRuntime().addShutdownHook(Thread { f.close() })

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
    Pair("s[", "HeapShortArray")
)

fun remap(s: String): String {
    if (s in types) return types[s]!!
    else return s
}

while (true) {
    val args = sysin.nextLine().split(",").map { it.split(".") }

    val funname = args[0][1]
    val ar = args.subList(1, args.size).map { "${it[0]}: ${remap(it[1])}" }.joinToString(", ")
    val call = args.subList(1, args.size).map { it[0] }.joinToString(", ")
    println("fun $funname(${ar}) = callVoidFunc(\"$funname\", $call)")
    f.write("fun $funname(${ar}) = callVoidFunc(\"$funname\", $call)\n")
}