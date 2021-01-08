APP_STL := c++_static

APP_CPPFLAGS := -frtti -DCC_ENABLE_CHIPMUNK_INTEGRATION=1 -std=c++11 -fsigned-char -fexceptions 
APP_LDFLAGS := -latomic
APP_ABI :=armeabi-v7a
APP_PLATFORM := android-19
APP_CPPFLAGS += -DNDEBUG
APP_OPTIM := release