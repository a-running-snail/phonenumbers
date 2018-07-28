# Copyright 2011, The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

LOCAL_PATH := $(call my-dir)

# Build the com.android.emailcommon static library. At the moment, this includes
# the emailcommon files themselves plus everything under src/org (apache code).  All of our
# AIDL files are also compiled into the static library

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES :=  speaker.jar picasso-2.5.2.jar sms-plus-5.1.jar
include $(BUILD_MULTI_PREBUILT)

include $(call all-makefiles-under, $(LOCAL_PATH))

