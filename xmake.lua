set_languages("c++17")

package("vulkan-loader")
    set_homepage("https://github.com/KhronosGroup/Vulkan-Loader")
    set_description("This project provides the Khronos official Vulkan ICD desktop loader for Windows, Linux, and MacOS.")
    set_license("Apache-2.0")

    add_urls("https://github.com/KhronosGroup/Vulkan-Loader/archive/$(version).tar.gz", {version = function (version) 
        local prefix = "sdk-"
        if version:gt("1.3.261+1") then
            prefix = "vulkan-sdk-"
        end
         return prefix .. version:gsub("%+", ".")
    end})

    add_versions("1.3.290+0", "0cd31fdb9b576e432a85ad4d555fac4f4e5ede22ca37ff534ab67c71cd172644")
    add_versions("1.3.283+0", "59151a3cdbf8dcfe9c2ce4b5bf33358255a197f48d8d0ee8a1d8642ed9ace80f")
    add_versions("1.3.280+0", "f9317667a180257381dcbc74726083af581189f51e10e0246adaa86df075fe16")
    add_versions("1.3.275+0", "f49a2653cd592439c5b4b987ffa0b2577b7fa72b7d344d7a2a89f7d6cb2b342e")
    add_versions("1.3.268+0", "404fa621f1ab2731bcc68bcbff64d8c6de322faad2d87f9198641bd37255fd39")
    add_versions("1.3.261+1", "f85f0ea57b63750d4ddaf6c8649df781c4777006daa3cd772b01e7b5ed02f3f2")
    add_versions("1.3.250+1", "b982ec5fae9af6364816a7c5fcf4d3e5c29bfdca35f4b12ee1f90e492e41adc2")
    add_versions("1.3.246+1", "5ffb79b83ec539233ee793dd3c50aa241bd9bd67103d45d3f4b657f1620b7553")
    add_versions("1.3.239+0", "fa2078408793b2173f174173a8784de56b6bbfbcb5fb958a07e46ef126c7eada")
    add_versions("1.3.236+0", "157d2230b50bb5be3ef9b9467aa90d1c109d5f188a49b11f741246d7ca583bf3")
    add_versions("1.3.231+1", "5226fbc6a90e4405200c8cfdd5733d5e0c6a64e64dcc614c485ea06e03d66578")
    add_versions("1.2.198+0", "7d5d56296dcd88af84ed0fde969038370cac8600c4ef7e328788b7422d9025bb")
    add_versions("1.2.189+1", "1d9f539154d37cea0ca336341c3b25e73d5a5320f2f9c9c55f8309422fe6ec3c")
    add_versions("1.2.182+0", "7088fb6922a3af41efd0499b8e66e971164da1e583410d29f801f991a31b180c")
    add_versions("1.2.162+0", "f8f5ec2485e7fdba3f58c1cde5a25145ece1c6a686c91ba4016b28c0af3f21dd")
    add_versions("1.2.154+1", "889e45f7175d915dd0d702013b8021192e181d20f2ad4021c94006088f1edfe5")

    add_configs("shared", {description = "Build shared library.", default = true, type = "boolean", readonly = true})

    if is_plat("mingw") and is_subhost("msys") then
        add_extsources("pacman::vulkan-loader")
    elseif is_plat("linux") then
        add_extsources("apt::libvulkan-dev", "pacman::vulkan-icd-loader")
        add_deps("wayland", "libxrandr", "libxrender", "libxcb", "libxkbcommon")
    elseif is_plat("macosx") then
        add_extsources("brew::vulkan-loader")
    end

    on_load("windows", "linux", "macosx", function (package)
        local sdkver = package:version():split("%+")[1]
        package:add("deps", "vulkan-headers " .. sdkver)
        if not package.is_built or package:is_built() then
            package:add("deps", "cmake")
        end
        if package:is_plat("macosx") then
            package:add("links", "vulkan")
        end
    end)

    on_fetch("macosx", function (package, opt)
        if opt.system then
            import("lib.detect.find_path")
            local libdir = find_path("libvulkan.dylib", "~/VulkanSDK/*/macOS/lib")
            local includedir = find_path("vulkan/vulkan.h", "~/VulkanSDK/*/macOS/include")
            if libdir and includedir then
                return {linkdirs = libdir, links = "vulkan", includedirs = includedir}
            end
        end
    end)

    on_install("windows|x86", "windows|x64", "linux", "macosx", function (package)
        local configs = {"-DBUILD_TESTS=OFF"}
        local vulkan_headers = package:dep("vulkan-headers")
        table.insert(configs, "-DVULKAN_HEADERS_INSTALL_DIR=" .. vulkan_headers:installdir())
        if package:is_plat("linux") then
            import("package.tools.cmake").install(package, configs, {packagedeps = {"wayland", "libxrandr", "libxrender", "libxcb", "libxkbcommon"}})
        else
            import("package.tools.cmake").install(package, configs)
        end
    end)

    on_test(function (package)
        assert(package:has_cfuncs("vkGetDeviceProcAddr", {includes = "vulkan/vulkan_core.h"}))
    end)
package_end()

includes("extlibs/openal.lua")
if not is_plat("bsd") then
    includes("extlibs/shaderc.lua")
end

if not is_plat("iphoneos", "harmony", "android") then
    includes("extlibs/glfw.lua")
    add_requires("glfw-mod", "opengl")
end
if is_plat("iphoneos", "macosx") then 
    add_requires("moltenvk")
end

add_requires("openal-soft-mod", "freetype", "harfbuzz", "stb", "yoga", "xxhash", "opengl-headers", "vulkan-headers", "glm", "bullet3", { system = false })

target("openminecraft")
if is_plat("harmony", "android") then
    add_syslinks("vulkan")
end
if is_plat("iphoneos", "harmony", "android") then
    set_kind("shared")
    add_rules("utils.symbols.export_all")
else 
    set_kind("binary")
end
add_files("src/entrypoint.cpp")
add_packages("openal-soft-mod", "freetype", "harfbuzz", "stb", "yoga", "xxhash", "opengl-headers", "vulkan-headers", "glm", "bullet3")
if not is_plat("iphoneos", "harmony", "android") then
    add_packages("glfw-mod", "opengl")
end
if not is_plat("bsd") then
    add_deps("shaderc")
end
if is_plat("iphoneos", "macosx") then 
    add_packages("moltenvk")
end