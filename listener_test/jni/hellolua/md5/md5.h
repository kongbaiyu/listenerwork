// Copyright 2007 Google Inc. All Rights Reserved.
// Author: liuli@google.com (Liu Li)
#ifndef COMMON_MD5_H__
#define COMMON_MD5_H__

#ifndef _WIN32
#include <stdint.h>
#else

#endif

typedef unsigned int md_u32;
typedef unsigned char md_u8;

struct MD5Context {
	md_u32 buf[4];
	md_u32 bits[2];
	md_u8 in[64];
};

#ifdef __cplusplus
extern "C" {
#endif  // __cplusplus

	void MD5Init(struct MD5Context *ctx);

	void MD5Update(struct MD5Context *ctx, unsigned char const *buf, unsigned len);

	void MD5Final(unsigned char digest[16], struct MD5Context *ctx);

	void EncryptionMD5(char psd[]);

#ifdef __cplusplus
}
#endif

#endif  // COMMON_MD5_H__
