#ifndef OM_CLASS_FILE_HPP
#define OM_CLASS_FILE_HPP

#include <cstdint>
#include <exception>
#include <istream>
#include <map>
#include <memory>
#include <vector>

#include "openminecraft/io/om_io_parser.hpp"
#include "openminecraft/log/om_log_common.hpp"
#include "openminecraft/util/om_util_result.hpp"

#define JVM_VERSION_1_1 45
#define JVM_VERSION_1_2 46
#define JVM_VERSION_1_3 47
#define JVM_VERSION_1_4 48
#define JVM_VERSION_5 49
#define JVM_VERSION_6 50
#define JVM_VERSION_7 51
#define JVM_VERSION_8 52
#define JVM_VERSION_9 53
#define JVM_VERSION_10 54
#define JVM_VERSION_11 55
#define JVM_VERSION_12 56
#define JVM_VERSION_13 57
#define JVM_VERSION_14 58
#define JVM_VERSION_15 59
#define JVM_VERSION_16 60
#define JVM_VERSION_17 61
#define JVM_VERSION_18 62
#define JVM_VERSION_19 63
#define JVM_VERSION_20 64
#define JVM_VERSION_21 65
#define JVM_VERSION_22 66
#define JVM_VERSION_23 67
#define JVM_VERSION_24 68

#define JVM_Acc_Public 0x0001
#define JVM_Acc_Private 0x0002
#define JVM_Acc_Protected 0x0004
#define JVM_Acc_Static 0x0008
#define JVM_Acc_Final 0x0010
#define JVM_Acc_Super 0x0020
#define JVM_Acc_Synchronized 0x0020
#define JVM_Acc_Bridge 0x0040
#define JVM_Acc_Varargs 0x0080
#define JVM_Acc_Native 0x0100
#define JVM_Acc_Interface 0x0200
#define JVM_Acc_Abstract 0x0400
#define JVM_Acc_Strict 0x0800
#define JVM_Acc_Synthetic 0x1000
#define JVM_Acc_Annotation 0x2000
#define JVM_Acc_Enum 0x4000
#define JVM_Acc_Module 0x8000

namespace openminecraft::vm::classfile
{
enum class OMClassConstantType : uint8_t
{
    Utf8 = 1,
    Integer = 3,
    Float = 4,
    Long = 5,
    Double = 6,
    Class = 7,
    String = 8,
    FieldRef = 9,
    MethodRef = 10,
    InterfaceMethodRef = 11,
    NameAndType = 12,
    MethodHandle = 15,  // Requires Java 7+
    MethodType = 16,    // Requires Java 7+
    Dynamic = 17,       // Requires Java 11+
    InvokeDynamic = 18, // Requires Java 7+
    Module = 19,        // Requires Java 9+
    Package = 20        // Requires Java 9+
};

class OMClassConstant
{
  public:
    virtual ~OMClassConstant();
    virtual OMClassConstantType type() = 0;
    template <typename T> T *to()
    {
        return reinterpret_cast<T *>(this);
    }
};

class OMClassConstantMethodRef : public OMClassConstant
{
  public:
    OMClassConstantMethodRef(uint16_t ci, uint16_t nti);
    virtual OMClassConstantType type() override;
    const uint16_t classIndex;
    const uint16_t nameAndTypeIndex;
};

class OMClassConstantFieldRef : public OMClassConstant
{
  public:
    OMClassConstantFieldRef(uint16_t ci, uint16_t nti);
    virtual OMClassConstantType type() override;
    const uint16_t classIndex;
    const uint16_t nameAndTypeIndex;
};

class OMClassConstantInterfaceMethodRef : public OMClassConstant
{
  public:
    OMClassConstantInterfaceMethodRef(uint16_t ci, uint16_t nti);
    virtual OMClassConstantType type() override;
    const uint16_t classIndex;
    const uint16_t nameAndTypeIndex;
};

class OMClassConstantClass : public OMClassConstant
{
  public:
    OMClassConstantClass(uint16_t ni);
    virtual OMClassConstantType type() override;
    const uint16_t nameIndex;
};

class OMClassConstantNameAndType : public OMClassConstant
{
  public:
    OMClassConstantNameAndType(uint16_t ni, uint16_t di);
    virtual OMClassConstantType type() override;
    const uint16_t nameIndex;
    const uint16_t descIndex;
};

class OMClassConstantUtf8 : public OMClassConstant
{
  public:
    OMClassConstantUtf8(std::string data);
    virtual OMClassConstantType type() override;
    const std::string data;
};

class OMClassConstantString : public OMClassConstant
{
  public:
    OMClassConstantString(uint16_t si);
    virtual OMClassConstantType type() override;
    const uint16_t stringIndex;
};

class OMClassConstantInteger : public OMClassConstant
{
  public:
    OMClassConstantInteger(int data);
    virtual OMClassConstantType type() override;
    const int data;
};

class OMClassConstantFloat : public OMClassConstant
{
  public:
    OMClassConstantFloat(float data);
    virtual OMClassConstantType type() override;
    const float data;
};

class OMClassConstantLong : public OMClassConstant
{
  public:
    OMClassConstantLong(int64_t data);
    virtual OMClassConstantType type() override;
    const int64_t data;
};

class OMClassConstantDouble : public OMClassConstant
{
  public:
    OMClassConstantDouble(double data);
    virtual OMClassConstantType type() override;
    const double data;
};

class OMClassConstantMethodHandle : public OMClassConstant
{
  public:
    OMClassConstantMethodHandle(uint8_t rk, uint16_t ri);
    virtual OMClassConstantType type() override;
    const uint8_t refKind;
    const uint16_t refIndex;
};

class OMClassConstantMethodType : public OMClassConstant
{
  public:
    OMClassConstantMethodType(uint16_t di);
    virtual OMClassConstantType type() override;
    const uint16_t descIndex;
};

class OMClassConstantDynamic : public OMClassConstant
{
  public:
    OMClassConstantDynamic(uint16_t bmai, uint16_t nti);
    virtual OMClassConstantType type() override;
    const uint16_t bootstrapMethodAttrIndex;
    const uint16_t nameAndTypeIndex;
};

class OMClassConstantInvokeDynamic : public OMClassConstant
{
  public:
    OMClassConstantInvokeDynamic(uint16_t bmai, uint16_t nti);
    virtual OMClassConstantType type() override;
    const uint16_t bootstrapMethodAttrIndex;
    const uint16_t nameAndTypeIndex;
};

class OMClassConstantModule : public OMClassConstant
{
  public:
    OMClassConstantModule(uint16_t ni);
    virtual OMClassConstantType type() override;
    const uint16_t nameIndex;
};

class OMClassConstantPackage : public OMClassConstant
{
  public:
    OMClassConstantPackage(uint16_t ni);
    virtual OMClassConstantType type() override;
    const uint16_t nameIndex;
};

enum OMClassAttrType
{
    ConstantValue,
    Code,
    StackMapTable,
    Exceptions,
    InnerClasses,
    EnclosingMethod,
    Synthetic,
    Signature,
    SourceFile,
    SourceDebugExtension,
    LineNumberTable,
    LocalVariableTable,
    LocalVariableTypeTable,
    Deprecated,
    RuntimeVisibleAnnotations,
    RuntimeInvisibleAnnotations,
    RuntimeVisibleParameterAnnotations,
    RuntimeInvisibleParameterAnnotations,
    RuntimeVisibleTypeAnnotations,
    RuntimeInvisibleTypeAnnotations,
    AnnotationDefault,
    BootstrapMethods,
    MethodParameters,
    Module,
    ModulePackages,
    ModuleMainClass,
    NestHost,
    NestMembers,
    Record,
    PermittedSubclasses
};

class OMClassAttr
{
  public:
    virtual ~OMClassAttr();
    virtual OMClassAttrType type() = 0;
    template <typename T> T *to()
    {
        return (T *)this;
    }
};

class OMClassAttrConstantValue : public OMClassAttr
{
  public:
    OMClassAttrConstantValue(uint16_t vi);
    virtual OMClassAttrType type() override;
    const uint16_t valueIndex;
};

struct OMClassAttrCodeExcTable
{
    uint16_t startPc;
    uint16_t endPc;
    uint16_t handlerPc;
    uint16_t catchType;
};

class OMClassAttrCode : public OMClassAttr
{
  public:
    OMClassAttrCode(uint16_t ms, uint16_t ml, uint32_t cl, std::vector<uint8_t> c, uint16_t etl,
                    std::vector<OMClassAttrCodeExcTable> et, uint16_t ac, std::vector<std::shared_ptr<OMClassAttr>> a);
    virtual OMClassAttrType type() override;
    const uint16_t maxStack;
    const uint16_t maxLocals;
    const uint32_t codeLength;
    const std::vector<uint8_t> code;
    const uint16_t excTableLength;
    const std::vector<OMClassAttrCodeExcTable> excTable;
    const uint16_t attributesCount;
    const std::vector<std::shared_ptr<OMClassAttr>> attributes;
};

enum OMClassAttrVerifyType : uint8_t
{
    Top,
    Integer,
    Float,
    Double,
    Long,
    Null,
    UninitializedThis,
    Object,
    Uninitialized
};

struct OMClassAttrVerifyTypeInfo
{
    OMClassAttrVerifyType tag;
    uint16_t arg;
};

struct OMClassAttrVerifySameFrame
{
    uint8_t tag;
};
struct OMClassAttrVerifySameLocals1StackItemFrame
{
    uint8_t tag;
    OMClassAttrVerifyTypeInfo stack;
};
struct OMClassAttrVerifySameLocals1StackItemFrameExt
{
    uint8_t tag;
    uint16_t offset;
    OMClassAttrVerifyTypeInfo stack;
};
struct OMClassAttrVerifyChopFrame
{
    uint8_t tag;
    uint16_t offset;
};
struct OMClassAttrVerifySameFrameExt
{
    uint8_t tag;
    uint16_t offset;
};
struct OMClassAttrVerifyAppendFrame
{
    uint8_t tag;
    uint16_t offset;
    std::vector<OMClassAttrVerifyTypeInfo> locals;
};
struct OMClassAttrVerifyFullFrame
{
    uint8_t tag;
    uint16_t offset;
    uint16_t numberOfLocals;
    std::vector<OMClassAttrVerifyTypeInfo> locals;
    uint16_t numberOfStackItems;
    std::vector<OMClassAttrVerifyTypeInfo> stackItems;
};
union OMClassAttrVerifyStackMapFrame {
    OMClassAttrVerifyStackMapFrame();
    ~OMClassAttrVerifyStackMapFrame();
    uint8_t tag;
    OMClassAttrVerifySameFrame sameFrame;
    OMClassAttrVerifySameLocals1StackItemFrame sameLocals1StackItemFrame;
    OMClassAttrVerifySameLocals1StackItemFrameExt sameLocals1StackItemFrameExt;
    OMClassAttrVerifyChopFrame chopFrame;
    OMClassAttrVerifySameFrameExt sameFrameExt;
    OMClassAttrVerifyAppendFrame appendFrame;
    OMClassAttrVerifyFullFrame fullFrame;
};

class OMClassAttrStackMapTable : public OMClassAttr
{
  public:
    OMClassAttrStackMapTable(uint16_t noe, std::vector<std::shared_ptr<OMClassAttrVerifyStackMapFrame>> e);
    virtual OMClassAttrType type() override;
    const uint16_t numberOfEntries;
    const std::vector<std::shared_ptr<OMClassAttrVerifyStackMapFrame>> entries;
};

class OMClassAttrExceptions : public OMClassAttr
{
  public:
    OMClassAttrExceptions(uint16_t noe, std::vector<uint16_t> eit);
    virtual OMClassAttrType type() override;
    const uint16_t numberOfExceptions;
    const std::vector<uint16_t> exceptionIndexTable;
};

struct OMClassAttrInnerClassInfo
{
    uint16_t innerClassInfoIndex;
    uint16_t outerClassInfoIndex;
    uint16_t innerNameIndex;
    uint16_t innerClassAccessFlags;
};

class OMClassAttrInnerClass : public OMClassAttr
{
  public:
    OMClassAttrInnerClass(uint16_t numberOfClasses, std::vector<OMClassAttrInnerClassInfo> classes);
    virtual OMClassAttrType type() override;
    const uint16_t numberOfClasses;
    const std::vector<OMClassAttrInnerClassInfo> classes;
};

class OMClassAttrEnclosingMethod : public OMClassAttr
{
  public:
    OMClassAttrEnclosingMethod(uint16_t ci, uint16_t mi);
    virtual OMClassAttrType type() override;
    const uint16_t classIndex;
    const uint16_t methodIndex;
};

class OMClassAttrSynthetic : public OMClassAttr
{
  public:
    OMClassAttrSynthetic();
    virtual OMClassAttrType type() override;
};

class OMClassAttrSignature : public OMClassAttr
{
  public:
    OMClassAttrSignature(uint16_t si);
    virtual OMClassAttrType type() override;
    const uint16_t signatureIndex;
};

class OMClassAttrSourceFile : public OMClassAttr
{
  public:
    OMClassAttrSourceFile(uint16_t si);
    virtual OMClassAttrType type() override;
    const uint16_t sourcefileIndex;
};

class OMClassAttrSourceDebugExtension : public OMClassAttr
{
  public:
    OMClassAttrSourceDebugExtension(std::vector<uint8_t> de);
    virtual OMClassAttrType type() override;
    const std::vector<uint8_t> debugExt;
};

class OMClassAttrLineNumberTable : public OMClassAttr
{
  public:
    OMClassAttrLineNumberTable(uint16_t lntl, std::map<uint16_t, uint16_t> lnt);
    virtual OMClassAttrType type() override;
    const uint16_t lineNumberTableLength;
    const std::map<uint16_t, uint16_t> lineNumberTable;
};

struct OMClassAttrLocalVar
{
    uint16_t startPc, length, nameIndex, descIndex, index;
};

class OMClassAttrLocalVarTable : public OMClassAttr
{
  public:
    OMClassAttrLocalVarTable(uint16_t lvtl, std::vector<OMClassAttrLocalVar> lvt);
    virtual OMClassAttrType type() override;
    const uint16_t localVarTableLength;
    const std::vector<OMClassAttrLocalVar> localVarTable;
};

class OMClassAttrLocalVarTypeTable : public OMClassAttr
{
  public:
    OMClassAttrLocalVarTypeTable(uint16_t lvtl, std::vector<OMClassAttrLocalVar> lvt);
    virtual OMClassAttrType type() override;
    const uint16_t localVarTableLength;
    const std::vector<OMClassAttrLocalVar> localVarTable;
};

class OMClassAttrDeprecated : public OMClassAttr
{
  public:
    OMClassAttrDeprecated();
    virtual OMClassAttrType type() override;
};

class OMClassAttrNestHost : public OMClassAttr
{
  public:
    OMClassAttrNestHost(uint16_t hci);
    virtual OMClassAttrType type() override;
    const uint16_t hostClassIndex;
};

class OMClassAttrNestMembers : public OMClassAttr
{
  public:
    OMClassAttrNestMembers(uint16_t noc, std::vector<uint16_t> classes);
    virtual OMClassAttrType type() override;
    const uint16_t numberOfClasses;
    const std::vector<uint16_t> classes;
};

class OMClassAttrPermittedSubclasses : public OMClassAttr
{
  public:
    OMClassAttrPermittedSubclasses(uint16_t noc, std::vector<uint16_t> classes);
    virtual OMClassAttrType type() override;
    const uint16_t numberOfClasses;
    const std::vector<uint16_t> classes;
};

class OMClassAttrModuleMainClass : public OMClassAttr
{
  public:
    OMClassAttrModuleMainClass(uint16_t mci);
    virtual OMClassAttrType type() override;
    const uint16_t mainClassIndex;
};

class OMClassAttrModulePackages : public OMClassAttr
{
  public:
    OMClassAttrModulePackages(uint16_t pc, std::vector<uint16_t> pi);
    virtual OMClassAttrType type() override;
    const uint16_t packageCount;
    const std::vector<uint16_t> packageIndex;
};

struct OMClassAnnotationElemValue
{
    uint8_t tag;

    uint16_t constValueIndex;
    struct
    {
        uint16_t typeNameIndex;
        uint16_t constNameIndex;
    } enumConstValue;

    uint16_t classInfoIndex;

    // Cast to OMClassAnnotation* while using
    std::shared_ptr<void> annotationValue;

    struct
    {
        uint16_t numValues;
        std::vector<OMClassAnnotationElemValue> values;
    } arrayValue;
};

struct OMClassAnnotation
{
    uint16_t type;
    uint16_t numPairs;
    std::map<uint16_t, std::shared_ptr<OMClassAnnotationElemValue>> pairs;
};

class OMClassAttrRuntimeVisibleAnnotations : public OMClassAttr
{
  public:
    OMClassAttrRuntimeVisibleAnnotations(uint16_t na, std::vector<std::shared_ptr<OMClassAnnotation>> data);
    virtual OMClassAttrType type() override;
    const uint16_t numAnnotations;
    const std::vector<std::shared_ptr<OMClassAnnotation>> annotations;
};

class OMClassAttrRuntimeInvisibleAnnotations : public OMClassAttr
{
  public:
    OMClassAttrRuntimeInvisibleAnnotations(uint16_t na, std::vector<std::shared_ptr<OMClassAnnotation>> data);
    virtual OMClassAttrType type() override;
    const uint16_t numAnnotations;
    const std::vector<std::shared_ptr<OMClassAnnotation>> annotations;
};

class OMClassAttrAnnotationDefault : public OMClassAttr
{
  public:
    OMClassAttrAnnotationDefault(std::shared_ptr<OMClassAnnotationElemValue> v);
    virtual OMClassAttrType type() override;
    const std::shared_ptr<OMClassAnnotationElemValue> value;
};

struct OMClassParam
{
    uint16_t nameIndex;
    uint16_t accessFlags;
};

class OMClassAttrMethodParameters : public OMClassAttr
{
  public:
    OMClassAttrMethodParameters(uint8_t pc, std::vector<OMClassParam> p);
    virtual OMClassAttrType type() override;
    const uint16_t paramCount;
    const std::vector<OMClassParam> params;
};

struct OMClassBootMethods
{
    uint16_t ref;
    uint16_t numArgs;
    std::vector<uint16_t> args;
};

class OMClassAttrBootMethods : public OMClassAttr
{
  public:
    OMClassAttrBootMethods(uint16_t n, std::vector<OMClassBootMethods> d);
    virtual OMClassAttrType type() override;
    const uint16_t numBootMethods;
    const std::vector<OMClassBootMethods> bootMethods;
};

struct OMClassRecordCompInfo
{
    uint16_t nameIndex;
    uint16_t descIndex;
    uint16_t attrCount;
    std::vector<std::shared_ptr<OMClassAttr>> attrs;
};

class OMClassAttrRecord : public OMClassAttr
{
  public:
    OMClassAttrRecord(uint16_t c, std::vector<OMClassRecordCompInfo> i);
    virtual OMClassAttrType type() override;
    const uint16_t numComps;
    const std::vector<OMClassRecordCompInfo> comps;
};

struct OMClassParamAnnotations
{
    uint16_t numAnnotations;
    std::vector<std::shared_ptr<OMClassAnnotation>> annotations;
};

class OMClassRuntimeVisibleParameterAnnotations : public OMClassAttr
{
  public:
    OMClassRuntimeVisibleParameterAnnotations(uint8_t n, std::vector<OMClassParamAnnotations> d);
    virtual OMClassAttrType type() override;
    const uint8_t numParams;
    const std::vector<OMClassParamAnnotations> params;
};

class OMClassRuntimeInvisibleParameterAnnotations : public OMClassAttr
{
  public:
    OMClassRuntimeInvisibleParameterAnnotations(uint8_t n, std::vector<OMClassParamAnnotations> d);
    virtual OMClassAttrType type() override;
    const uint8_t numParams;
    const std::vector<OMClassParamAnnotations> params;
};

struct OMClassFieldInfo
{
    uint16_t accessFlags;
    uint16_t nameIndex;
    uint16_t descIndex;
    uint16_t attrCount;
    std::vector<std::shared_ptr<OMClassAttr>> attrs;
};

struct OMClassMethodInfo
{
    uint16_t accessFlags;
    uint16_t nameIndex;
    uint16_t descIndex;
    uint16_t attrCount;
    std::vector<std::shared_ptr<OMClassAttr>> attrs;
};

struct OMClassFile
{
    uint32_t magicNumber;
    uint16_t minor;
    uint16_t major;
    uint16_t constantPoolCount;
    std::vector<std::shared_ptr<OMClassConstant>> constants;
    uint16_t accessFlags;
    uint16_t thisClass;
    uint16_t superClass;
    uint16_t interfacesCount;
    std::vector<uint16_t> interfaces;
    uint16_t fieldsCount;
    std::vector<std::shared_ptr<OMClassFieldInfo>> fields;
    uint16_t methodsCount;
    std::vector<std::shared_ptr<OMClassMethodInfo>> methods;
    uint16_t attrCount;
    std::vector<std::shared_ptr<OMClassAttr>> attrs;
};

class OMClassFileParser : public io::OMParser
{
    using ConstantMapping = std::map<uint16_t, std::shared_ptr<OMClassConstant>>;

  public:
    OMClassFileParser(std::shared_ptr<std::istream> stream);
    ~OMClassFileParser();
    util::OMResult<std::shared_ptr<OMClassFile>, std::exception> parse();

  private:
    std::shared_ptr<log::OMLogger> logger;

    util::OMResult<std::shared_ptr<OMClassConstant>, std::exception> parseConstant(uint16_t *idx);
    ConstantMapping buildConstantMapping(std::vector<std::shared_ptr<OMClassConstant>> c);
    std::shared_ptr<OMClassFieldInfo> parseField(ConstantMapping m);
    std::shared_ptr<OMClassAttr> parseAttr(ConstantMapping m);
    std::shared_ptr<OMClassMethodInfo> parseMethod(ConstantMapping m);
    std::shared_ptr<OMClassAnnotation> parseAnnotation();
    std::shared_ptr<OMClassAnnotationElemValue> parseAnnotationValue();
    std::string toStdUtf8(std::vector<uint8_t> data, int length);
};
} // namespace openminecraft::vm::classfile

#endif
