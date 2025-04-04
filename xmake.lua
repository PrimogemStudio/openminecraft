set_languages("c++17")

includes("extlibs/libpatches.lua")
includes("extlibs/vulkan.lua")

function mobile()
    return is_plat("iphoneos", "harmony", "android")
end

function apple()
    return is_plat("iphoneos", "macosx", "visionos")
end

function vulkandyn()
    return is_plat("linux", "cross", "bsd", "android")
end

-- includes("extlibs/openal.lua")
includes("extlibs/shaderc.lua")

if not mobile() then
    includes("extlibs/glfw.lua")
    add_requires("opengl", { system = false })
    if not is_plat("linux", "cross", "bsd", "macosx", "iphoneos", "visionos") then
        add_requires("vulkan-loader", { system = false })
    end
end
if apple() then 
    add_requires("moltenvk")
end

add_requires("llvm", { 
    system = false,
    configs = {
        components = {"core"},
        shared = false,
        cxxflags = "-fno-rtti"
    }
})

add_requires("freetype", "harfbuzz", "stb", "yoga", "vulkan-headers", "glm", "bullet3", "vulkan-hpp", { system = false })
if not is_plat("harmony") then
    add_requires("libsdl3")
end

target("openminecraft")
if is_plat("harmony") then
    add_syslinks("vulkan")
end
if mobile() then
    set_kind("shared")
    add_rules("utils.symbols.export_all")
else 
    set_kind("binary")
end
add_files("src/entrypoint.cpp")
add_packages("freetype", "harfbuzz", "stb", "yoga", "vulkan-headers", "glm", "bullet3", "vulkan-hpp", "llvm")
add_deps("shaderc")
if not is_plat("harmony") then
    add_packages("libsdl3")
end
if not mobile() then
    add_packages("opengl")
    if not vulkandyn() and not apple() then
        add_packages("vulkan-loader")
    end
end
if apple() then 
    add_packages("moltenvk")
end
if vulkandyn() then
    add_defines("OM_VULKAN_DYNAMIC=")
end
if is_plat("iphoneos") then
    add_frameworks("OpenGLES")
end