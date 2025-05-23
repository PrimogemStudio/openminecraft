package("vulkan-hpp")
    set_kind("library", {headeronly = true})
    set_homepage("https://github.com/KhronosGroup/Vulkan-Hpp/")
    set_description("Open-Source Vulkan C++ API")
    set_license("Apache-2.0")

    add_urls("https://github.com/KhronosGroup/Vulkan-Hpp.git")
    -- when adding a new sdk version, please also update vulkan-headers, vulkan-loader, vulkan-utility-libraries, spirv-headers, spirv-reflect, glslang and volk packages
    add_versions("v1.4.309", "16da8bd29e5d0b921c92d406d194759c0df6a2b8")

    add_configs("modules", {description = "Build with C++20 modules support.", default = false, type = "boolean"})

    on_load(function (package)
        if not package:config("modules") then
            package:add("deps", "cmake")
            if package:is_plat("linux") then
                package:add("extsources", "pacman::vulkan-headers")
            end
        end
    end)


    on_install("windows", "harmony", "cross", "linux", "macosx", "mingw", "android", "iphoneos", "bsd", "visionos", function (package)
        local arch_prev
        local plat_prev
        if (package:is_plat("mingw") or package:is_cross()) and package.plat_set then
            arch_prev = package:arch()
            plat_prev = package:plat()
            package:plat_set(os.host())
            package:arch_set(os.arch())
        end
        io.replace("CMakeLists.txt", "-Werror", "", {plain = true})
        io.replace("CMakeLists.txt", "/WX", "", {plain = true})
        import("package.tools.cmake").build(package, {buildir = "build"})
        if arch_prev and plat_prev then
            package:plat_set(plat_prev)
            package:arch_set(arch_prev)
        end
        os.mkdir("build")
        if is_host("windows") then
            os.cp(path.join("**", "VulkanHppGenerator.exe"), "build")
        else
            os.cp(path.join("**", "VulkanHppGenerator"), "build")
        end
        os.runv(path.join("build", "VulkanHppGenerator"))
        if not package:config("modules") then
            os.cp("Vulkan-Headers/include", package:installdir())
            os.cp("vulkan/*.hpp", package:installdir(path.join("include", "vulkan")))
        else
            io.writefile("xmake.lua", [[ 
                target("vulkan-hpp")
                    set_kind("static")
                    set_languages("c++20")
                    add_headerfiles("Vulkan-Headers/include/(**.h)")
                    add_headerfiles("Vulkan/(**.h)")
                    add_headerfiles("Vulkan-Headers/include/(**.hpp)")
                    add_headerfiles("Vulkan/(**.hpp)")
                    add_includedirs("Vulkan")
                    add_includedirs("Vulkan-Headers/include")
                    add_files("Vulkan/vulkan.cppm", {public = true})
            ]])
            local configs = {}
            import("package.tools.xmake").install(package, configs)
        end
    end)
package_end()

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

    add_versions("1.4.309+0", "ddfeca84a868899fbb2c7f28b7c8fd1006e34b2b13ce653a63bddfb65cbc8d13")
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
        add_deps("libxrandr", "libxrender", "libxcb", "libxkbcommon")
    elseif is_plat("macosx") then
        add_extsources("brew::vulkan-loader")
    end

    on_load("windows", "linux", "macosx", "cross", function (package)
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

    on_install("windows", "linux", "macosx", "cross", function (package)
        local configs = {"-DBUILD_TESTS=OFF", "-DBUILD_WSI_WAYLAND_SUPPORT=OFF"}
        local vulkan_headers = package:dep("vulkan-headers")
        table.insert(configs, "-DVULKAN_HEADERS_INSTALL_DIR=" .. vulkan_headers:installdir())
        if package:is_plat("linux") then
            import("package.tools.cmake").install(package, configs, {packagedeps = {"libxrandr", "libxrender", "libxcb", "libxkbcommon"}})
        else
            import("package.tools.cmake").install(package, configs)
        end
    end)
package_end()

package("moltenvk")
    set_homepage("https://github.com/KhronosGroup/MoltenVK")
    set_description("MoltenVK is a Vulkan Portability implementation.")
    set_license("Apache-2.0")

    add_urls("https://github.com/KhronosGroup/MoltenVK/archive/refs/tags/$(version).tar.gz",
             "https://github.com/KhronosGroup/MoltenVK.git")

    add_versions("v1.2.11", "bfa115e283831e52d70ee5e13adf4d152de8f0045996cf2a33f0ac541be238b1")
    add_versions("v1.2.10", "3435d34ea2dafb043dd82ac5e9d2de7090462ab7cea6ad8bcc14d9c34ff99e9c")
    add_versions("v1.2.9", "f415a09385030c6510a936155ce211f617c31506db5fbc563e804345f1ecf56e")
    add_versions("v1.2.8", "85beaf8abfcc54d9da0ff0257ae311abd9e7aa96e53da37e1c37d6bc04ac83cd")
    add_versions("v1.2.7", "3166edcfdca886b4be1a24a3c140f11f9a9e8e49878ea999e3580dfbf9fe4bec")
    add_versions("v1.2.0", "6e7af2dad0530b2b404480dbe437ca4670c6615cc2ec6cf6a20ed04d9d75e0bd")
    add_versions("v1.1.5", "2cdcb8dbf2acdcd8cbe70b109dadc05a901038c84970afbe4863e5e23f33deae")
    add_versions("v1.1.4", "f9bba6d3bf3648e7685c247cb6d126d62508af614bc549cedd5859a7da64967e")
    add_versions("v1.1.0", "0538fa1c23ddae495c7f82ccd0db90790a90b7017a258ca7575fbae8021f3058")

    if is_plat("macosx") then
        add_extsources("brew::molten-vk")
    end

    add_links("MoltenVK")

    if is_plat("macosx", "iphoneos") then
        add_frameworks("Metal", "Foundation", "QuartzCore", "CoreGraphics", "IOSurface")
        if is_plat("macosx") then
            add_frameworks("IOKit", "AppKit")
        else
            add_frameworks("UIKit")
        end
    end

    on_fetch("macosx", function (package, opt)
        if opt.system then
            import("lib.detect.find_path")
            local frameworkdir = find_path("vulkan.framework", "~/VulkanSDK/*/macOS/Frameworks")
            if frameworkdir then
                return {frameworkdirs = frameworkdir, frameworks = "vulkan", rpathdirs = frameworkdir}
            end
        end
    end)

    on_install("macosx", "iphoneos", function (package)
        local plat = package:is_plat("iphoneos") and "iOS" or "macOS"
        local configs = {"--" .. plat:lower()}
        if package:debug() then
            table.insert(configs, "--debug")
        end
        os.vrunv("./fetchDependencies", configs)
        local conf = package:debug() and "Debug" or "Release"
        os.vrun("xcodebuild build -quiet -project MoltenVKPackaging.xcodeproj -scheme \"MoltenVK Package (" ..plat .. " only)\" -configuration \"" .. conf)
        os.mv("Package/" .. conf .. "/MoltenVK/include", package:installdir())
        --os.mv("Package/" .. conf .. "/MoltenVK/dylib/" ..plat .. "/*", package:installdir("lib"))
        os.mv("Package/" .. conf .. "/MoltenVK/MoltenVK.xcframework/" .. plat:lower() .. "-*/*.a", package:installdir("lib"))
        --if package:config("shared") then
        --    os.mv("Package/" .. conf .. "/MoltenVK/dynamic/dylib/" .. plat .. "/*.dylib", package:installdir("lib"))
        --else
        --    os.mv("Package/" .. conf .. "/MoltenVK/static/MoltenVK.xcframework/" .. plat:lower() .. "-*/*.a", package:installdir("lib"))
        --end
        os.mv("Package/" .. conf .. "/MoltenVK/static/MoltenVK.xcframework/" .. plat:lower() .. "-*/*.a", package:installdir("lib"))
        os.mv("Package/" .. conf .. "/MoltenVKShaderConverter/Tools/*", package:installdir("bin"))
        os.mv("Package/" .. conf .. "/MoltenVKShaderConverter/MoltenVKShaderConverter.xcframework/" .. plat:lower() .. "-*/*.a", package:installdir("lib"))
        os.mv("Package/" .. conf .. "/MoltenVKShaderConverter/include/*.h", package:installdir("include"))
        package:addenv("PATH", "bin")
    end)
package_end()