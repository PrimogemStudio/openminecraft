#ifndef OM_MEM_RECORD_HPP
#define OM_MEM_RECORD_HPP

#include <cstdint>
#include <cstdlib>
namespace openminecraft::mem::castorice
{
enum MemModifyType
{
    Allocation,
    Free
};

struct MemModifyInfo
{
    MemModifyType type;
    void *addr;
    size_t length;
    uint8_t tag;
};

size_t heapSize(void *p);
void rec(MemModifyInfo i);
} // namespace openminecraft::mem::castorice

#endif