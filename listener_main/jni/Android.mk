LOCAL_PATH := $(call my-dir)

LOCAL_CERTIFICATE := platform
include $(CLEAR_VARS)
LOCAL_LDLIBS    := -lm -llog

#$(call import-add-path,$(LOCAL_PATH)/../..)

LOCAL_MODULE := listener_shared

LOCAL_MODULE_FILENAME := liblistener

LOCAL_SRC_FILES := \
hellolua/lua/lapi.c \
hellolua/lua/lauxlib.c \
hellolua/lua/lbaselib.c \
hellolua/lua/lcode.c \
hellolua/lua/ldblib.c \
hellolua/lua/ldebug.c \
hellolua/lua/ldo.c \
hellolua/lua/ldump.c \
hellolua/lua/lfunc.c \
hellolua/lua/lgc.c \
hellolua/lua/linit.c \
hellolua/lua/liolib.c \
hellolua/lua/llex.c \
hellolua/lua/lmathlib.c \
hellolua/lua/lmem.c \
hellolua/lua/loadlib.c \
hellolua/lua/lobject.c \
hellolua/lua/lopcodes.c \
hellolua/lua/loslib.c \
hellolua/lua/lparser.c \
hellolua/lua/lstate.c \
hellolua/lua/lstring.c \
hellolua/lua/lstrlib.c \
hellolua/lua/ltable.c \
hellolua/lua/ltablib.c \
hellolua/lua/ltm.c \
hellolua/lua/lua.c \
hellolua/lua/lundump.c \
hellolua/lua/lvm.c \
hellolua/lua/lzio.c \
hellolua/lua/print.c \
hellolua/schedule.cpp \
hellolua/Listener.cpp \
hellolua/common.cpp \
hellolua/net/GameSocket.cpp \
hellolua/net/UdpSocket.cpp \
hellolua/net/Net.cpp \
hellolua/md5/md5.cpp \
hellolua/main.cpp \


LOCAL_C_INCLUDES := \
$(LOCAL_PATH)/hellolua \
$(LOCAL_PATH)/hellolua/net \
$(LOCAL_PATH)/hellolua/md5 \
$(LOCAL_PATH)/hellolua/lua

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libocr-sdk
LOCAL_SRC_FILES := libocr-sdk.so
include $(PREBUILT_SHARED_LIBRARY)


