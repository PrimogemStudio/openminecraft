#include "openminecraft/log/om_log_common.hpp"
#include "openminecraft/log/om_log_threadname.hpp"
#include "openminecraft/vm/om_class_file.hpp"
#include <SDL3/SDL_init.h>
#include <SDL3/SDL_video.h>
#include <fstream>
#include <vector>
#ifdef OM_VULKAN_DYNAMIC
#define VULKAN_HPP_DISPATCH_LOADER_DYNAMIC 1
#endif
#include "vulkan/vulkan.hpp"
#include "shaderc/shaderc.hpp"
#include <sstream>

#ifdef OM_VULKAN_DYNAMIC
VULKAN_HPP_DEFAULT_DISPATCH_LOADER_DYNAMIC_STORAGE
#endif

using namespace openminecraft::log;
using namespace openminecraft::log::multithraad;
using namespace openminecraft::vm::classfile;

int main()
{
    registerCurrentThreadName("engineMain");
    auto logger = new OMLogger("test");

#ifdef OM_VULKAN_DYNAMIC
    vk::DynamicLoader dl;
    PFN_vkGetInstanceProcAddr vkGetInstanceProcAddr = dl.getProcAddress<PFN_vkGetInstanceProcAddr>("vkGetInstanceProcAddr");
    VULKAN_HPP_DEFAULT_DISPATCHER.init(vkGetInstanceProcAddr);
#endif
    vk::Instance instance = vk::createInstance({}, nullptr);
#ifdef OM_VULKAN_DYNAMIC
    VULKAN_HPP_DEFAULT_DISPATCHER.init(instance);
#endif
    std::vector<vk::PhysicalDevice> physicalDevices = instance.enumeratePhysicalDevices();
    omLog(logger->info, "Vulkan devices: " << physicalDevices.size());
    vk::Device device = physicalDevices[0].createDevice({}, nullptr);
#ifdef OM_VULKAN_DYNAMIC
    VULKAN_HPP_DEFAULT_DISPATCHER.init(device);
#endif
    shaderc::Compiler comp;
    omLog(logger->info, "Shaderc available: " << comp.IsValid());
    omLog(logger->info, "hello *OMLogger = " << logger << "!");
}
