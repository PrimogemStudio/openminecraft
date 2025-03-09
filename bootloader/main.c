#include <dlfcn.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

typedef int (*jvmEntryPoint)(
    int argc, char** argv,
    int jargc, char** jargv,
    int appclassc, char** appclassv,
    char* fullversion,
    char* dotversion,
    char* programname,
    char* launchername,
    bool jargs,
    bool cpw,
    bool javaw,
    int ergo
);

int main(int argc, char** argv) {
    char* pthBase = strrchr(argv[0], '/');
    char* opt = "/jre/lib/libjli.so";
    char* exec = "/jre/bin/java";
    memcpy(pthBase, opt, 19);

    void* jliLib = dlopen(argv[0], RTLD_GLOBAL | RTLD_LAZY);
    memcpy(pthBase, exec, 14);
    char* bootArgs[4];
    bootArgs[0] = argv[0];
    bootArgs[1] = "--enable-native-access=ALL-UNNAMED";
    bootArgs[2] = "-jar";
    bootArgs[3] = "./app/openminecraft.jar";
    ((jvmEntryPoint) dlsym(jliLib, "JLI_Launch"))(
        4, bootArgs,
        0, NULL,
        0, NULL,
        "1.8.0-internal", "1.8.0", "java", "openjdk",
        false, true, false, 0
    );

    return 0;
}