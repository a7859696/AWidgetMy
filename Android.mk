
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_SRC_FILES += \
        src/com/flyaudio/proxyservice/aidl/IProxyConnet.aidl
LOCAL_PACKAGE_NAME := li
LOCAL_CERTIFICATE := platform

LOCAL_PROGUARD_FLAG_FILES := proguard.flags
LOCAL_STATIC_JAVA_LIBRARIES :=  android-support-v4
         
include $(BUILD_PACKAGE)
#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := lib3party:libs/android-support-v4.jar 
#include $(BUILD_PREBUILT )

include $(call all-makefiles-under,$(LOCAL_PATH))
