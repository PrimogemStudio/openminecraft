#ifndef OM_LOG_COMMON_HPP
#define OM_LOG_COMMON_HPP

#include <cstdint>
#include <ostream>
#include <string>
#include <format>

#define debugf(n, ...) debug(std::format(n, __VA_ARGS__))
#define infof(n, ...) info(std::format(n, __VA_ARGS__))
#define warnf(n, ...) warn(std::format(n, __VA_ARGS__))
#define errorf(n, ...) error(std::format(n, __VA_ARGS__))
#define fatalf(n, ...) fatal(std::format(n, __VA_ARGS__))
#define om_log_ptr(p) (void*) p

namespace openminecraft::log
{
    enum OMLogType: uint8_t
    {
        Debug, 
        Info,
        Warn,
        Error,
        Fatal
    };

    class OMLogger
    {
        public:
            OMLogger(std::string name, std::ostream& stream, bool format);
            OMLogger(std::string name);
            ~OMLogger();
            void debug(std::string msg);
            void info(std::string msg);
            void warn(std::string msg);
            void error(std::string msg);
            void fatal(std::string msg);

        protected:
            std::string loggerName;
            std::ostream* target;
            bool enableFormat;

        private:
            void log(OMLogType type, std::string msg);
    };
}

#endif
