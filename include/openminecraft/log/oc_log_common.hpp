#ifndef OC_LOG_COMMON_HPP
#define OC_LOG_COMMON_HPP

#include <string>

namespace openminecraft::log {
    class oc_logger {
        public:
            oc_logger(std::string name);
            ~oc_logger();
            virtual void debug(std::string msg) = 0;
            virtual void info(std::string msg) = 0;
            virtual void warn(std::string msg) = 0;
            virtual void error(std::string msg) = 0;
            virtual void fatal(std::string msg) = 0;

        protected:
            std::string loggerName;
    };
}

#endif