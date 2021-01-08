#pragma once
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)

#include<string>
extern "C" {
#include <lua.h>
#include <lauxlib.h>
#include <lualib.h>
}


#ifndef KAGUYA_USE_CPP11
#if defined(__cpp_decltype) || __cplusplus >= 201103L || (defined(_MSC_VER) && _MSC_VER >= 1800)
#define KAGUYA_USE_CPP11 1
#else
#define KAGUYA_USE_CPP11 0
#endif
#endif

#ifndef KAGUYA_NO_SET_AT_PANIC
//msvc 2013 and 2015(no update) strange error.
//and lua_State status not update
#define KAGUYA_NO_SET_AT_PANIC 1
#endif

#if KAGUYA_USE_CPP11
#include <functional>
#include <tuple>
#include <memory>
#include <utility>
#include <type_traits>
#else
#include <boost/function.hpp>
#include <boost/tuple/tuple.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/make_shared.hpp>
#include <boost/type_traits.hpp>
#include <boost/static_assert.hpp>


	
#define KAGUYA_PP_CAT(F,B) F##B
#define KAGUYA_PP_REPEAT0(MACRO)
#define KAGUYA_PP_REPEAT1(MACRO) MACRO(1)
#define KAGUYA_PP_REPEAT2(MACRO) KAGUYA_PP_REPEAT1(MACRO) MACRO(2)
#define KAGUYA_PP_REPEAT3(MACRO) KAGUYA_PP_REPEAT2(MACRO) MACRO(3)
#define KAGUYA_PP_REPEAT4(MACRO) KAGUYA_PP_REPEAT3(MACRO) MACRO(4)
#define KAGUYA_PP_REPEAT5(MACRO) KAGUYA_PP_REPEAT4(MACRO) MACRO(5)
#define KAGUYA_PP_REPEAT6(MACRO) KAGUYA_PP_REPEAT5(MACRO) MACRO(6)
#define KAGUYA_PP_REPEAT7(MACRO) KAGUYA_PP_REPEAT6(MACRO) MACRO(7)
#define KAGUYA_PP_REPEAT8(MACRO) KAGUYA_PP_REPEAT7(MACRO) MACRO(8)
#define KAGUYA_PP_REPEAT9(MACRO) KAGUYA_PP_REPEAT8(MACRO) MACRO(9)
#define KAGUYA_PP_REPEAT10(MACRO) KAGUYA_PP_REPEAT9(MACRO) MACRO(10)
#define KAGUYA_PP_REPEAT(COUNT,MACRO) KAGUYA_PP_CAT(KAGUYA_PP_REPEAT,COUNT)(MACRO)


#define KAGUYA_PP_REPEAT_DEF0(MACRO)
#define KAGUYA_PP_REPEAT_DEF1(MACRO) MACRO(1)
#define KAGUYA_PP_REPEAT_DEF2(MACRO) KAGUYA_PP_REPEAT_DEF1(MACRO) MACRO(2)
#define KAGUYA_PP_REPEAT_DEF3(MACRO) KAGUYA_PP_REPEAT_DEF2(MACRO) MACRO(3)
#define KAGUYA_PP_REPEAT_DEF4(MACRO) KAGUYA_PP_REPEAT_DEF3(MACRO) MACRO(4)
#define KAGUYA_PP_REPEAT_DEF5(MACRO) KAGUYA_PP_REPEAT_DEF4(MACRO) MACRO(5)
#define KAGUYA_PP_REPEAT_DEF6(MACRO) KAGUYA_PP_REPEAT_DEF5(MACRO) MACRO(6)
#define KAGUYA_PP_REPEAT_DEF7(MACRO) KAGUYA_PP_REPEAT_DEF6(MACRO) MACRO(7)
#define KAGUYA_PP_REPEAT_DEF8(MACRO) KAGUYA_PP_REPEAT_DEF7(MACRO) MACRO(8)
#define KAGUYA_PP_REPEAT_DEF9(MACRO) KAGUYA_PP_REPEAT_DEF8(MACRO) MACRO(9)
#define KAGUYA_PP_REPEAT_DEF10(MACRO) KAGUYA_PP_REPEAT_DEF9(MACRO) MACRO(10)
#define KAGUYA_PP_REPEAT_DEF(COUNT,MACRO) KAGUYA_PP_CAT(KAGUYA_PP_REPEAT_DEF,COUNT)(MACRO)

#define KAGUYA_PP_REPEAT_ARG0(MACRO)
#define KAGUYA_PP_REPEAT_ARG1(MACRO) MACRO(1)
#define KAGUYA_PP_REPEAT_ARG2(MACRO) KAGUYA_PP_REPEAT_ARG1(MACRO), MACRO(2)
#define KAGUYA_PP_REPEAT_ARG3(MACRO) KAGUYA_PP_REPEAT_ARG2(MACRO), MACRO(3)
#define KAGUYA_PP_REPEAT_ARG4(MACRO) KAGUYA_PP_REPEAT_ARG3(MACRO), MACRO(4)
#define KAGUYA_PP_REPEAT_ARG5(MACRO) KAGUYA_PP_REPEAT_ARG4(MACRO), MACRO(5)
#define KAGUYA_PP_REPEAT_ARG6(MACRO) KAGUYA_PP_REPEAT_ARG5(MACRO), MACRO(6)
#define KAGUYA_PP_REPEAT_ARG7(MACRO) KAGUYA_PP_REPEAT_ARG6(MACRO), MACRO(7)
#define KAGUYA_PP_REPEAT_ARG8(MACRO) KAGUYA_PP_REPEAT_ARG7(MACRO), MACRO(8)
#define KAGUYA_PP_REPEAT_ARG9(MACRO) KAGUYA_PP_REPEAT_ARG8(MACRO), MACRO(9)
#define KAGUYA_PP_REPEAT_ARG10(MACRO) KAGUYA_PP_REPEAT_ARG9(MACRO), MACRO(10)
#define KAGUYA_PP_REPEAT_ARG(COUNT,MACRO) KAGUYA_PP_CAT(KAGUYA_PP_REPEAT_ARG,COUNT)(MACRO)
		
#define KAGUYA_PP_VARIADIC_TARG_CONCAT_REP(N) ,KAGUYA_PP_CAT(A,N)
#define KAGUYA_PP_VARIADIC_TARG_REP(N) KAGUYA_PP_CAT(A,N)

#define KAGUYA_PP_TEMPLATE_ARG_REPEAT_CONCAT(N) KAGUYA_PP_REPEAT(N, KAGUYA_PP_VARIADIC_TARG_CONCAT_REP)
#define KAGUYA_PP_TEMPLATE_ARG_REPEAT(N) KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PP_VARIADIC_TARG_REP)


#define KAGUYA_PP_ARG_DEF_CONCAT_REP(N) ,KAGUYA_PP_CAT(A,N) KAGUYA_PP_CAT(a,N)
#define KAGUYA_PP_ARG_DEF_REP(N) KAGUYA_PP_CAT(A,N) KAGUYA_PP_CAT(a,N)

#define KAGUYA_PP_ARG_DEF_REPEAT_CONCAT(N) KAGUYA_PP_REPEAT(N, KAGUYA_PP_ARG_DEF_CONCAT_REP)
#define KAGUYA_PP_ARG_DEF_REPEAT(N) KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PP_ARG_DEF_REP)


#define KAGUYA_PP_ARG_CONCAT_REP(N) ,KAGUYA_PP_CAT(a,N)
#define KAGUYA_PP_ARG_REP(N) KAGUYA_PP_CAT(a,N)

#define KAGUYA_PP_ARG_REPEAT_CONCAT(N) KAGUYA_PP_REPEAT(N, KAGUYA_PP_ARG_CONCAT_REP)
#define KAGUYA_PP_ARG_REPEAT(N) KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PP_ARG_REP)



#define KAGUYA_PP_VARIADIC_TDEF_CONCAT_REP(N) ,KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_VARIADIC_TDEF_REP(N) KAGUYA_PP_CAT(typename A,N)

#define KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N) KAGUYA_PP_REPEAT(N,KAGUYA_PP_VARIADIC_TDEF_CONCAT_REP)
#define KAGUYA_PP_TEMPLATE_DEF_REPEAT(N) KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_VARIADIC_TDEF_REP)


#endif


#ifndef KAGUYA_ERROR_NO_THROW
#define KAGUYA_ERROR_NO_THROW 1
#endif

#ifndef KAGUYA_USE_RVALUE_REFERENCE
#if KAGUYA_USE_CPP11
#define KAGUYA_USE_RVALUE_REFERENCE 1
#else
#define KAGUYA_USE_RVALUE_REFERENCE 0
#endif
#endif


#ifdef KAGUYA_NO_VECTOR_AND_MAP_TO_TABLE
#define KAGUYA_NO_STD_VECTOR_TO_TABLE
#define KAGUYA_NO_STD_MAP_TO_TABLE
#endif


#ifndef KAGUYA_DETECT_USE_DEPRECATED_FEATURE
#define KAGUYA_DETECT_USE_DEPRECATED_FEATURE 0
#endif


#ifndef KAGUYA_DEPRECATED_FEATURE
#if KAGUYA_USE_CPP11 && defined(__has_cpp_attribute)
#if  __has_cpp_attribute(deprecated)
//C++ standard depecated
#define KAGUYA_DEPRECATED_FEATURE(MSG) [[deprecated(MSG)]]
#endif
#endif
#endif
#ifndef KAGUYA_DEPRECATED_FEATURE
#if defined(_MSC_VER)
//MSVC depecated
#define KAGUYA_DEPRECATED_FEATURE(MSG) __declspec(deprecated(MSG)) 
#elif defined(__GNUC__) || defined(__clang__)
#define KAGUYA_DEPRECATED_FEATURE(MSG) __attribute__((deprecated(MSG)))
#else
#define KAGUYA_DEPRECATED_FEATURE(MSG)
#endif

#endif

namespace kaguya
{
#if defined(_MSC_VER) && _MSC_VER <= 1500
	typedef unsigned char uint8_t;
	typedef int int32_t;
	typedef long long int64_t;
#endif

	namespace standard
	{
#if KAGUYA_USE_CPP11
		using namespace std;
#define KAGUYA_STATIC_ASSERT static_assert

#else
		using namespace boost;
#define KAGUYA_STATIC_ASSERT BOOST_STATIC_ASSERT_MSG
#endif
	}

	//compatibility for Lua5.1
#if LUA_VERSION_NUM < 502
	inline void luaL_setmetatable(lua_State *L, const char *tname) {
		luaL_getmetatable(L, tname);
		lua_setmetatable(L, -2);
	}
	inline void *luaL_testudata(lua_State *L, int index, const char *tname) {
		void *p = lua_touserdata(L, index);
		if (p == NULL)
		{
			return 0;
		}
		if (lua_getmetatable(L, index))
		{
			luaL_getmetatable(L, tname);
			if (!lua_rawequal(L, -1, -2))
			{
				p = 0;
			}
			lua_pop(L, 2);//pop metatables
			return p;
		}
		return 0;
	}
	inline void luaL_requiref(lua_State *L, const char *modname,
		lua_CFunction openf, int glb) {

		lua_pushcfunction(L, openf);
		lua_pushstring(L, modname);
		lua_call(L, 1, 1);

		if (glb) {
			lua_pushvalue(L, -1);
			lua_setglobal(L, modname);
		}
	}
#endif
#if LUA_VERSION_NUM > 502
	typedef lua_Integer luaInt;
#else
	typedef int32_t luaInt;
#endif
}

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <vector>
#include <set>
#include <map>
#include <cassert>
#include <algorithm>
#include <ostream>
#include <istream>
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>
#include <cstring>

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>


namespace kaguya
{
	namespace traits
	{
		using namespace kaguya::standard;

		template<bool B, class T = void>struct enable_if {};
		template<class T>struct enable_if<true, T> { typedef T type; };

		template< typename T >
		struct is_void :integral_constant<bool, false>
		{
		};
		template<>
		struct is_void<void> :integral_constant<bool, true>
		{
		};


		template<class T> struct is_const : integral_constant<bool, false> {};
		template<class T> struct is_const<const T> : integral_constant<bool, true> {};

		template<class T> struct is_const_reference : integral_constant<bool, false> {};
		template<class T> struct is_const_reference<const T&> : integral_constant<bool, true> {};

		template< typename T >
		struct remove_const_and_reference {
			typedef T type;
		};
		template< typename T >
		struct remove_const_and_reference<T&> {
			typedef T type;
		};
		template< typename T >
		struct remove_const_and_reference<const T> {
			typedef T type;
		};
		template< typename T >
		struct remove_const_and_reference<const T&> {
			typedef T type;
		};
		template< typename T >
		struct remove_const_reference {
			typedef T type;
		};
		template< typename T >
		struct remove_const_reference<const T&> {
			typedef T type;
		};

		template<class T> struct is_std_vector : integral_constant<bool, false> {};
		template<class T, class A> struct is_std_vector<std::vector<T, A> > : integral_constant<bool, true> {};
		template<class T> struct is_std_map : integral_constant<bool, false> {};
		template<class K, class V, class C, class A> struct is_std_map<std::map<K, V, C, A> > : integral_constant<bool, true> {};
	}

	template<typename T, typename Enable = void>
	struct lua_type_traits
	{
		typedef void Registerable;

		typedef typename traits::remove_const_and_reference<T>::type NCRT;
		typedef const NCRT& get_type;
		typedef const NCRT& push_type;

		static bool checkType(lua_State* l, int index);
		static bool strictCheckType(lua_State* l, int index);

		static get_type get(lua_State* l, int index);
		static int push(lua_State* l, push_type v);
#if KAGUYA_USE_RVALUE_REFERENCE
		static int push(lua_State* l, NCRT&& v);
#endif
	};

	template< typename T, typename Enable = void>
	struct is_registerable : traits::integral_constant<bool, false> {};
	template< typename T>
	struct is_registerable<T, typename lua_type_traits<T>::Registerable> : traits::integral_constant<bool, true> {};


	template< typename T, typename Enable = void>
	struct is_usertype : traits::integral_constant<bool, false> {};
	template< typename T>
	struct is_usertype<T, typename lua_type_traits<T>::Registerable> : traits::integral_constant<bool, true> {};
}
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>
#include <cstring>
#include <typeinfo>
#include <algorithm>

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <exception>

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


namespace kaguya
{
	namespace util
	{
		class ScopedSavedStack {
			lua_State * state_;
			int saved_top_index_;

		public:
			explicit ScopedSavedStack(lua_State * state)
				: state_(state),
				saved_top_index_(lua_gettop(state_))
			{}
			explicit ScopedSavedStack(lua_State * state, int count)
				: state_(state),
				saved_top_index_(count)
			{}

			~ScopedSavedStack() {
				if (state_) {
					lua_settop(state_, saved_top_index_);
				}
			}

		private:
			ScopedSavedStack(ScopedSavedStack const &);
			ScopedSavedStack & operator=(ScopedSavedStack const &);
		};
		inline void traceBack(lua_State* state, const char* message, int level = 0)
		{
#if LUA_VERSION_NUM >= 502
			luaL_traceback(state, state, message, level);
#else
			lua_pushstring(state, message);
#endif
		}

		inline void stackDump(lua_State *L) {
			int i;
			int top = lua_gettop(L);
			for (i = 1; i <= top; i++) {  /* repeat for each level */
				int t = lua_type(L, i);
				switch (t) {

				case LUA_TSTRING:  /* strings */
					printf("`%s'", lua_tostring(L, i));
					break;

				case LUA_TBOOLEAN:  /* booleans */
					printf(lua_toboolean(L, i) ? "true" : "false");
					break;

				case LUA_TNUMBER:  /* numbers */
					printf("%g", lua_tonumber(L, i));
					break;
				case LUA_TUSERDATA:
					if (luaL_getmetafield(L, i, "__name") == LUA_TSTRING)
					{
						printf("userdata:%s", lua_tostring(L, -1));
						lua_pop(L, 1);
						break;
					}
				default:  /* other values */
					printf("%s", lua_typename(L, t));
					break;

				}
				printf("  ");  /* put a separator */
			}
			printf("\n");  /* end the listing */
		}

		inline void stackValueDump(std::ostream& os, lua_State* state, int stackIndex, int max_recursive = 2)
		{
			if (stackIndex < 0)
			{
				stackIndex += lua_gettop(state) + 1;
			}
			util::ScopedSavedStack save(state);
			int type = lua_type(state, stackIndex);
			switch (type)
			{
			case LUA_TNONE:
				os << "none";
				break;
			case LUA_TNIL:
				os << "nil";
				break;
			case LUA_TBOOLEAN:
				os << ((lua_toboolean(state, stackIndex) != 0) ? "true" : "false");
				break;
			case LUA_TNUMBER:
				os << lua_tonumber(state, stackIndex);
				break;
			case LUA_TSTRING:
				os << "'" << lua_tostring(state, stackIndex) << "'";
				break;
			case LUA_TTABLE:
			{
				os << "{";
				if (max_recursive <= 1)
				{
					os << "...";
				}
				else
				{
					lua_pushnil(state);
					if ((lua_next(state, stackIndex) != 0))
					{
						stackValueDump(os, state, -2, max_recursive - 1);
						os << "=";
						stackValueDump(os, state, -1, max_recursive - 1);
						lua_pop(state, 1);//pop value

						while (lua_next(state, stackIndex) != 0)
						{
							os << ",";
							stackValueDump(os, state, -2, max_recursive - 1);
							os << "=";
							stackValueDump(os, state, -1, max_recursive - 1);
							lua_pop(state, 1);//pop value
						}
					}
				}
				os << "}";
			}
			break;
			case LUA_TUSERDATA:
			case LUA_TLIGHTUSERDATA:
			case LUA_TTHREAD:
				os << lua_typename(state, type) << "(" << lua_topointer(state, stackIndex) << ")";
				break;
			case LUA_TFUNCTION:
				os << lua_typename(state, type);
				break;
			default:
				os << "unknown type value";
				break;
			}
		}

		inline int lua_resume_compat(lua_State *L, int nargs)
		{
			if (nargs < 0) { nargs = 0; }
#if LUA_VERSION_NUM >= 502
			return lua_resume(L, 0, nargs);
#else
			return lua_resume(L, nargs);
#endif
		}



		inline lua_State* toMainThread(lua_State* state)
		{
#if LUA_VERSION_NUM >= 502
			if (state)
			{
				lua_rawgeti(state, LUA_REGISTRYINDEX, LUA_RIDX_MAINTHREAD);
				lua_State* mainthread = lua_tothread(state, -1);
				lua_pop(state, 1);
				if (mainthread)
				{
					return mainthread;
				}
			}
#endif
			return state;
		}

#if KAGUYA_USE_CPP11
		inline int push_args(lua_State *l)
		{
			return 0;
		}
		template<class Arg, class...Args>
		inline int push_args(lua_State *l, Arg&& arg, Args&&... args)
		{
			int c = lua_type_traits<typename traits::remove_reference<Arg>::type>::push(l, std::forward<Arg>(arg));
			return c + push_args(l, std::forward<Args>(args)...);
		}
		template<class Arg, class...Args>
		inline int push_args(lua_State *l, const Arg& arg, Args&&... args)
		{
			int c = lua_type_traits<Arg>::push(l, arg);
			return c + push_args(l, std::forward<Args>(args)...);
		}
#else
		inline int push_args(lua_State *l)
		{
			return 0;
		}

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)
#define KAGUYA_PUSH_DEF(N) c+=lua_type_traits<KAGUYA_PP_CAT(A,N) >::push(l, KAGUYA_PP_CAT(a,N)); 
#define KAGUYA_PUSH_ARG_DEF(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>\
		inline int push_args(lua_State *l,KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG))\
		{\
			int c =0;\
			KAGUYA_PP_REPEAT(N,KAGUYA_PUSH_DEF)\
			return c;\
		}
		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_PUSH_ARG_DEF)
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_PUSH_DEF
#undef KAGUYA_PUSH_ARG_DEF
#endif


#if KAGUYA_USE_CPP11
			template<typename T>
		inline bool one_push(lua_State* state, T&& v)
		{
			int count = util::push_args(state, std::forward<T>(v));
			if (count > 1) { lua_pop(state, count - 1); }
			return count != 0;
		}
#else
			template<typename T>
		inline bool one_push(lua_State* state, const T& v)
		{
			int count = util::push_args(state, v);
			if (count > 1) { lua_pop(state, count - 1); }
			return count != 0;
		}
#endif
	}


}


namespace kaguya
{
	class LuaException :public std::exception
	{
		int status_;
		std::string what_;
		const char* what_c_;
	public:
		LuaException(int status, const char* what)throw() :status_(status), what_c_(what) {}
		LuaException(int status, const std::string& what) :status_(status), what_(what), what_c_(0) {}
		int status()const throw() { return status_; }
		const char* what() const throw() { return what_c_ ? what_c_ : what_.c_str(); }

		~LuaException()throw() {}
	};
	class KaguyaException :public std::exception
	{
		std::string what_;
		const char* what_c_;
	public:
		KaguyaException(const char* what)throw() : what_c_(what) {}
		KaguyaException(const std::string& what) : what_(what), what_c_(0) {}
		const char* what() const throw() { return what_c_ ? what_c_ : what_.c_str(); }

		~KaguyaException()throw() {}
	};
	class LuaTypeMismatch :public LuaException {
	public:
		LuaTypeMismatch(const char* what)throw() :LuaException(0, what) {}
		LuaTypeMismatch(const std::string& what) :LuaException(0, what) {}
	};
#if !KAGUYA_ERROR_NO_THROW
	class LuaRuntimeError :public LuaException {
	public:
		LuaRuntimeError(int status, const char* what)throw() :LuaException(status, what) {}
		LuaRuntimeError(int status, const std::string& what) :LuaException(status, what) {}
	};
	class LuaMemoryError :public LuaException {
	public:
		LuaMemoryError(int status, const char* what)throw() :LuaException(status, what) {}
	};
	class LuaRunningError :public LuaException {
	public:
		LuaRunningError(int status, const char* what)throw() :LuaException(status, what) {}
		LuaRunningError(int status, const std::string& what) :LuaException(status, what) {}
	};
	class LuaGCError :public LuaException {
	public:
		LuaGCError(int status, const char* what)throw() :LuaException(status, what) {}
		LuaGCError(int status, const std::string& what) :LuaException(status, what) {}
	};
	class LuaUnknownError :public LuaException {
	public:
		LuaUnknownError(int status, const char* what)throw() :LuaException(status, what) {}
		LuaUnknownError(int status, const std::string& what) :LuaException(status, what) {}
	};

	class LuaSyntaxError :public LuaException {
	public:
		LuaSyntaxError(int status, const std::string& what) :LuaException(status, what) {}
	};

	class LuaKaguyaError :public LuaException {
	public:
		LuaKaguyaError(const std::string& what) :LuaException(0, what) {}
	};
#endif

}

namespace kaguya
{
	namespace types
	{
		template<typename T>
		struct typetag {};
	}

#define KAGUYA_METATABLE_PREFIX "kaguya_object_type_"
#define KAGUYA_METATABLE_TYPE_NAME_KEY -212114

	template<typename T>
	inline const std::string& metatableName()
	{
		typedef typename traits::remove_cv<T>::type noncv_type;
		typedef typename traits::remove_pointer<noncv_type>::type noncvpointer_type;
		typedef typename traits::remove_const_and_reference<noncvpointer_type>::type noncvpointerref_type;

		static const std::string v = std::string(KAGUYA_METATABLE_PREFIX) + typeid(noncvpointerref_type*).name();

		return v;
	}
	template<typename T>
	const std::type_info& metatableType()
	{
		typedef typename traits::remove_cv<T>::type noncv_type;
		typedef typename traits::remove_pointer<noncv_type>::type noncvpointer_type;
		typedef typename traits::remove_const_and_reference<noncvpointer_type>::type noncvpointerref_type;
		return typeid(noncvpointerref_type*);
	}

	namespace class_userdata
	{
		template<typename T>bool get_metatable(lua_State* l)
		{
			luaL_getmetatable(l, metatableName<T>().c_str());
			return !lua_isnil(l, -1);
		}
		template<typename T>bool available_metatable(lua_State* l)
		{
			util::ScopedSavedStack save(l);
			return get_metatable<T>(l);
		}
		inline bool newmetatable(lua_State* l,const char* metatablename)
		{
			if (luaL_newmetatable(l, metatablename))
			{
#if LUA_VERSION_NUM < 503
				lua_pushstring(l, metatablename);
				lua_setfield(l, -2, "__name");
#endif
				lua_pushstring(l, metatablename);
				lua_rawseti(l, -2, KAGUYA_METATABLE_TYPE_NAME_KEY);
				return true;
			}
			return false;
		}
		template<typename T>bool newmetatable(lua_State* l)
		{
			return newmetatable(l, metatableName<T>().c_str());
		}
		template<typename T>void setmetatable(lua_State* l)
		{
			if (available_metatable<T>(l))
			{
				return luaL_setmetatable(l, metatableName<T>().c_str());
			}
			else
			{
				luaL_getmetatable(l, KAGUYA_METATABLE_PREFIX "unknown_type");

				if(lua_isnil(l, -1))
				{
					lua_pop(l,1);
					newmetatable(l, KAGUYA_METATABLE_PREFIX "unknown_type");
				}
				lua_setmetatable(l, -2);
			}
		}

		template<typename T>T* test_userdata(lua_State* l, int index)
		{
			return static_cast<T*>(luaL_testudata(l, index, metatableName<T>().c_str()));
		}

		template<typename T>inline void destructor(T* pointer)
		{
			if (pointer)
			{
				pointer->~T();
			}
		}
	}
	inline bool available_metatable(lua_State* l, const char* t)
	{
		util::ScopedSavedStack save(l);
		luaL_getmetatable(l, t);
		return !lua_isnil(l, -1);
	}
	template<typename T>
	bool available_metatable(lua_State* l, types::typetag<T> type = types::typetag<T>())
	{
		return class_userdata::available_metatable<T>(l);
	}

	struct ObjectWrapperBase
	{
		virtual bool is_native_type(const std::type_info& type) = 0;

		virtual const void* native_cget() = 0;
		virtual void* native_get() = 0;
		virtual const void* cget() = 0;
		virtual void* get() = 0;

		virtual const std::type_info& type() = 0;

//		virtual void addRef(lua_State* state, int index) {};

		ObjectWrapperBase() {}
		virtual ~ObjectWrapperBase() {}
	private:

		//noncopyable
		ObjectWrapperBase(const ObjectWrapperBase&);
		ObjectWrapperBase& operator=(const ObjectWrapperBase&);
	};

	template<class T>
	struct ObjectWrapper : ObjectWrapperBase
	{
#if KAGUYA_USE_CPP11
		template<class... Args>
		ObjectWrapper(Args&&... args) : object_(std::forward<Args>(args)...) {}
#else

		ObjectWrapper() : object_() {}
		template<class Arg1>
		ObjectWrapper(const Arg1& v1) : object_(v1) {}
		template<class Arg1, class Arg2>
		ObjectWrapper(const Arg1& v1, const Arg2& v2) : object_(v1, v2) {}
		template<class Arg1, class Arg2, class Arg3>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3) : object_(v1, v2, v3) {}
		template<class Arg1, class Arg2, class Arg3, class Arg4>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3, const Arg4& v4) : object_(v1, v2, v3, v4) {}
		template<class Arg1, class Arg2, class Arg3, class Arg4, class Arg5>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3, const Arg4& v4, const Arg5& v5) : object_(v1, v2, v3, v4, v5) {}
		template<class Arg1, class Arg2, class Arg3, class Arg4, class Arg5, class Arg6>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3, const Arg4& v4, const Arg5& v5, const Arg6& v6) : object_(v1, v2, v3, v4, v5, v6) {}
		template<class Arg1, class Arg2, class Arg3, class Arg4, class Arg5, class Arg6, class Arg7>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3, const Arg4& v4, const Arg5& v5, const Arg6& v6, const Arg7& v7) : object_(v1, v2, v3, v4, v5, v6, v7) {}
		template<class Arg1, class Arg2, class Arg3, class Arg4, class Arg5, class Arg6, class Arg7, class Arg8>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3, const Arg4& v4, const Arg5& v5, const Arg6& v6, const Arg7& v7, const Arg8& v8) : object_(v1, v2, v3, v4, v5, v6, v7, v8) {}
		template<class Arg1, class Arg2, class Arg3, class Arg4, class Arg5, class Arg6, class Arg7, class Arg8, class Arg9>
		ObjectWrapper(const Arg1& v1, const Arg2& v2, const Arg3& v3, const Arg4& v4, const Arg5& v5, const Arg6& v6, const Arg7& v7, const Arg8& v8, const Arg9& v9) : object_(v1, v2, v3, v4, v5, v6, v7, v8, v9) {}

#endif

		virtual bool is_native_type(const std::type_info& type)
		{
			return metatableType<T>() == type;
		}
		virtual const std::type_info& type()
		{
			return metatableType<T>();
		}

		virtual void* get()
		{
			return &object_;
		}
		virtual const void* cget()
		{
			return &object_;
		}
		virtual const void* native_cget() { return cget(); };
		virtual void* native_get() { return get(); };

	private:
		T object_;
	};


	struct ObjectSharedPointerWrapper : ObjectWrapperBase
	{

		template<typename T>
		ObjectSharedPointerWrapper(const standard::shared_ptr<T>& sptr) :object_(sptr), type_(metatableType<T>()),
			shared_ptr_type_(metatableType<standard::shared_ptr<T> >()) {}
#if KAGUYA_USE_RVALUE_REFERENCE
		template<typename T>
		ObjectSharedPointerWrapper(standard::shared_ptr<T>&& sptr) : object_(std::move(sptr)), type_(metatableType<T>()),
			shared_ptr_type_(metatableType<standard::shared_ptr<T> >()) {}
#endif
		virtual bool is_native_type(const std::type_info& type)
		{
			return shared_ptr_type_ == type;
		}
		virtual const std::type_info& type()
		{
			return type_;
		}
		virtual void* get()
		{
			return object_.get();
		}
		virtual const void* cget()
		{
			return object_.get();
		}
		virtual const void* native_cget() { return &object_; };
		virtual void* native_get() { return &object_; };

		const standard::shared_ptr<void> object()const { return object_; }
		const std::type_info& shared_ptr_type()const { return shared_ptr_type_; }
	private:
		standard::shared_ptr<void> object_;
		const std::type_info& type_;

		const std::type_info& shared_ptr_type_;
	};

	template<class T>
	struct ObjectSmartPointerWrapper : ObjectWrapperBase
	{
		ObjectSmartPointerWrapper(const T& sptr) :object_(sptr) {}
#if KAGUYA_USE_RVALUE_REFERENCE
		ObjectSmartPointerWrapper(T&& sptr) : object_(std::move(sptr)) {}
#endif
		virtual bool is_native_type(const std::type_info& type)
		{
			return metatableType<T>() == type;
		}
		virtual const std::type_info& type()
		{
			return metatableType<typename T::element_type>();
		}
		virtual void* get()
		{
			return object_.get();
		}
		virtual const void* cget()
		{
			return object_.get();
		}
		virtual const void* native_cget() { return &object_; };
		virtual void* native_get() { return &object_; };

	private:
		T object_;
	};

	template<class T>
	struct ObjectPointerWrapper : ObjectWrapperBase
	{
		ObjectPointerWrapper(T* ptr) :object_(ptr) {}

		virtual bool is_native_type(const std::type_info& type)
		{
			return metatableType<T>() == type;
		}
		virtual const std::type_info& type()
		{
			return metatableType<T>();
		}
		virtual void* get()
		{
			if (traits::is_const<T>::value)
			{
				return 0;
			}
			return const_cast<void*>(static_cast<const void*>(object_));
		}
		virtual const void* cget()
		{
			return object_;
		}
		virtual const void* native_cget() { return cget(); };
		virtual void* native_get() { return get(); };

		~ObjectPointerWrapper()
		{
//			for (std::vector<std::pair<lua_State*, int> >::iterator i = retain_ref_.begin(); i != retain_ref_.end(); ++i)
//			{
//				luaL_unref(i->first, LUA_REGISTRYINDEX, i->second);
//			}
		}

//		virtual void addRef(lua_State* state, int index) {
//			lua_pushvalue(state, index);
//			retain_ref_.push_back(std::pair<lua_State*, int>(state, luaL_ref(state, LUA_REGISTRYINDEX)));
//		};
	private:
		T* object_;
//		std::vector<std::pair<lua_State*, int> > retain_ref_;
	};

	//for internal use
	struct PointerConverter
	{
		template<typename T, typename F>
		static void* base_pointer_cast(void* from)
		{
			return static_cast<T*>(static_cast<F*>(from));
		}
		template<typename T, typename F>
		static standard::shared_ptr<void> base_shared_pointer_cast(const standard::shared_ptr<void>& from)
		{
			return standard::shared_ptr<T>(standard::static_pointer_cast<F>(from));
		}


		typedef void* (*convert_function_type)(void*);
		typedef standard::shared_ptr<void>(*shared_ptr_convert_function_type)(const standard::shared_ptr<void>&);
		typedef std::pair<std::string, std::string> convert_map_key;


		template<typename ToType, typename FromType>
		void add_type_conversion()
		{
			add_function(metatableType<ToType>(), metatableType<FromType>(), &base_pointer_cast<ToType, FromType>);
			add_function(metatableType<standard::shared_ptr<ToType> >(), metatableType<standard::shared_ptr<FromType> >(), &base_shared_pointer_cast<ToType, FromType>);
		}


		template<typename TO>
		TO* get_pointer(ObjectWrapperBase* from)const
		{
			const std::type_info& to_type = metatableType<TO>();
			if (to_type == from->type())
			{
				return static_cast<TO*>(from->get());
			}
			std::map<convert_map_key, std::vector<convert_function_type> >::const_iterator match = function_map_.find(convert_map_key(to_type.name(), from->type().name()));
			if (match != function_map_.end())
			{
				return static_cast<TO*>(pcvt_list_apply(from->get(), match->second));
			}
			return 0;

		}
		template<typename TO>
		const TO* get_const_pointer(ObjectWrapperBase* from)const
		{
			const std::type_info& to_type = metatableType<TO>();
			if (to_type == from->type())
			{
				return static_cast<const TO*>(from->cget());
			}
			std::map<convert_map_key, std::vector<convert_function_type> >::const_iterator match = function_map_.find(convert_map_key(to_type.name(), from->type().name()));
			if (match != function_map_.end())
			{
				return static_cast<const TO*>(pcvt_list_apply(const_cast<void*>(from->cget()), match->second));
			}
			return 0;
		}

		template<typename TO>
		standard::shared_ptr<TO> get_shared_pointer(ObjectWrapperBase* from)const
		{
			const std::type_info& to_type = metatableType<standard::shared_ptr<TO> >();
			ObjectSharedPointerWrapper* ptr = dynamic_cast<ObjectSharedPointerWrapper*>(from);
			if (!ptr) {
				return standard::shared_ptr<TO>();
			}
			if (to_type == from->type())
			{
				return standard::static_pointer_cast<TO>(ptr->object());
			}
			std::map<convert_map_key, std::vector<shared_ptr_convert_function_type> >::const_iterator match = shared_ptr_function_map_.find(convert_map_key(to_type.name(), ptr->shared_ptr_type().name()));
			if (match != shared_ptr_function_map_.end())
			{
				return standard::static_pointer_cast<TO>(pcvt_list_apply(ptr->object(), match->second));
			}
			return standard::shared_ptr<TO>();
		}
		standard::shared_ptr<void> get_shared_pointer(ObjectWrapperBase* from)const
		{
			ObjectSharedPointerWrapper* ptr = dynamic_cast<ObjectSharedPointerWrapper*>(from);
			if (ptr) {
				return ptr->object();
			}
			return standard::shared_ptr<void>();
		}


		template<class T>
		T* get_pointer(ObjectWrapperBase* from, types::typetag<T> tag)
		{
			return get_pointer<T>(from);
		}
		template<class T>
		const T* get_pointer(ObjectWrapperBase* from, types::typetag<const T> tag)
		{
			return get_const_pointer<T>(from);
		}
		template<class T>
		standard::shared_ptr<T> get_pointer(ObjectWrapperBase* from, types::typetag<standard::shared_ptr<T> > tag)
		{
			return get_shared_pointer<T>(from);
		}


		static int deleter(lua_State *state)
		{
			PointerConverter* ptr = (PointerConverter*)lua_touserdata(state, 1);
			ptr->~PointerConverter();
			return 0;
		}

		static PointerConverter& get(lua_State* state)
		{
			static char kaguya_ptrcvt_key_ptr;
			util::ScopedSavedStack save(state);
			lua_pushlightuserdata(state, &kaguya_ptrcvt_key_ptr);
			lua_gettable(state, LUA_REGISTRYINDEX);
			if (lua_isuserdata(state, -1))
			{
				return *static_cast<PointerConverter*>(lua_touserdata(state, -1));
			}
			else
			{
				void* ptr = lua_newuserdata(state, sizeof(PointerConverter));//dummy data for gc call
				if (!ptr) { throw std::runtime_error("critical error. maybe failed memory allocation"); }//critical error
				PointerConverter* converter = new(ptr) PointerConverter();
				if (!converter) { throw std::runtime_error("critical error. maybe failed memory allocation"); }//critical error

				lua_createtable(state, 0, 0);
				lua_pushcclosure(state, &deleter, 0);
				lua_setfield(state, -2, "__gc");
				lua_pushvalue(state, -1);
				lua_setfield(state, -2, "__index");
				lua_setmetatable(state, -2);//set to userdata
				lua_pushlightuserdata(state, &kaguya_ptrcvt_key_ptr);
				lua_pushvalue(state, -2);
				lua_settable(state, LUA_REGISTRYINDEX);
				return *converter;
			}
		}
	private:
		void add_function(const std::type_info& to_type, const std::type_info& from_type, convert_function_type f)
		{
			std::map<convert_map_key, std::vector<convert_function_type> > add_map;
			for (std::map<convert_map_key, std::vector<convert_function_type> >::iterator it = function_map_.begin();
			it != function_map_.end(); ++it)
			{
//				if (it->first.first == from_type.name())
//				{
//					std::vector<convert_function_type> newlist = it->second;
//					newlist.push_back(f);
//					add_map[convert_map_key(to_type.name(), it->first.second)] = newlist;
//				}

				if (it->first.second == to_type.name())
				{
					std::vector<convert_function_type> newlist;
					newlist.push_back(f);
					newlist.insert(newlist.end(), it->second.begin(), it->second.end());
					add_map[convert_map_key(it->first.first, from_type.name())] = newlist;
				}
			}
			function_map_.insert(add_map.begin(), add_map.end());

			std::vector<convert_function_type> flist; flist.push_back(f);
			function_map_[convert_map_key(to_type.name(), from_type.name())] = flist;
		}
		void add_function(const std::type_info& to_type, const std::type_info& from_type, shared_ptr_convert_function_type f)
		{
			std::map<convert_map_key, std::vector<shared_ptr_convert_function_type> > add_map;
			for (std::map<convert_map_key, std::vector<shared_ptr_convert_function_type> >::iterator it = shared_ptr_function_map_.begin();
			it != shared_ptr_function_map_.end(); ++it)
			{
//				if (it->first.first == from_type.name())
//				{
//					std::vector<shared_ptr_convert_function_type> newlist = it->second;
//					newlist.push_back(f);
//					add_map[convert_map_key(to_type.name(), it->first.second)] = newlist;
//				}

				if (it->first.second == to_type.name())
				{
					std::vector<shared_ptr_convert_function_type> newlist;
					newlist.push_back(f);
					newlist.insert(newlist.end(), it->second.begin(), it->second.end());
					add_map[convert_map_key(it->first.first, from_type.name())] = newlist;
				}
			}
			shared_ptr_function_map_.insert(add_map.begin(), add_map.end());

			std::vector<shared_ptr_convert_function_type> flist; flist.push_back(f);
			shared_ptr_function_map_[convert_map_key(to_type.name(), from_type.name())] = flist;
		}

		void* pcvt_list_apply(void* ptr, const std::vector<convert_function_type>& flist)const
		{
			for (std::vector<convert_function_type>::const_iterator i = flist.begin(); i != flist.end(); ++i)
			{
				ptr = (*i)(ptr);
			}
			return ptr;
		}
		standard::shared_ptr<void> pcvt_list_apply(standard::shared_ptr<void> ptr, const std::vector<shared_ptr_convert_function_type>& flist)const
		{
			for (std::vector<shared_ptr_convert_function_type>::const_iterator i = flist.begin(); i != flist.end(); ++i)
			{

#if KAGUYA_USE_CPP11
				ptr = (*i)(std::move(ptr));
#else
				ptr = (*i)(ptr);
#endif
			}
			return ptr;
		}


		PointerConverter() {}

		std::map<convert_map_key, std::vector<convert_function_type> > function_map_;
		std::map<convert_map_key, std::vector<shared_ptr_convert_function_type> > shared_ptr_function_map_;



		PointerConverter(PointerConverter&);
		PointerConverter& operator=(PointerConverter&);
	};

	namespace detail
	{
		inline bool object_wrapper_type_check(lua_State* l, int index)
		{
			if (lua_getmetatable(l, index))
			{
				lua_rawgeti(l, -1, KAGUYA_METATABLE_TYPE_NAME_KEY);
				const char* metatable_name = lua_tostring(l, -1);

				lua_pop(l, 2);
				return metatable_name != 0;
			}
			return false;
		}
	}

	inline ObjectWrapperBase* object_wrapper(lua_State* l, int index)
	{
		if (detail::object_wrapper_type_check(l, index))
		{
			ObjectWrapperBase* ptr = static_cast<ObjectWrapperBase*>(lua_touserdata(l, index));
			return ptr;
		}
		return 0;
	}

	template<typename RequireType>
	inline ObjectWrapperBase* object_wrapper(lua_State* l, int index,bool convert = true)
	{
		if (detail::object_wrapper_type_check(l, index))
		{
			ObjectWrapperBase* ptr = static_cast<ObjectWrapperBase*>(lua_touserdata(l, index));

			if (ptr->is_native_type(metatableType<RequireType>()))
			{
				return ptr;
			}
			else if (ptr->type() == metatableType<RequireType>())
			{
				return ptr;
			}
			else if(convert)
			{
				PointerConverter& pcvt = PointerConverter::get(l);
				return pcvt.get_pointer(ptr, types::typetag<RequireType>()) ? ptr : 0;
			}
			return 0;
		}
		return 0;
	}

	template<class T>
	T* get_pointer(lua_State* l, int index, types::typetag<T> tag)
	{
		int type = lua_type(l, index);

		if (type == LUA_TLIGHTUSERDATA)
		{
			return (T*)lua_topointer(l, index);
		}
		else if (type != LUA_TUSERDATA)
		{
			return 0;
		}
		else
		{
			ObjectWrapperBase* objwrapper = object_wrapper(l, index);
			if (objwrapper)
			{
				if (static_cast<ObjectWrapperBase*>(objwrapper)->is_native_type(metatableType<T>()))
				{
					return static_cast<T*>(objwrapper->native_get());
				}
				else if (objwrapper->type() == metatableType<T>())
				{
					return static_cast<T*>(objwrapper->get());
				}
				else
				{
					PointerConverter& pcvt = PointerConverter::get(l);
					return pcvt.get_pointer<T>(objwrapper);
				}
			}
		}
		return 0;
	}
	template<class T>
	const T* get_const_pointer(lua_State* l, int index, types::typetag<T> tag)
	{
		int type = lua_type(l, index);

		if (type == LUA_TLIGHTUSERDATA)
		{
			return (T*)lua_topointer(l, index);
		}
		else if (type != LUA_TUSERDATA)
		{
			return 0;
		}
		else
		{
			ObjectWrapperBase* objwrapper = object_wrapper(l, index);
			if (objwrapper)
			{
				if (objwrapper->is_native_type(metatableType<T>()))
				{
					return static_cast<const T*>(objwrapper->native_cget());
				}
				else if (objwrapper->type() == metatableType<T>())
				{
					return static_cast<const T*>(objwrapper->cget());
				}
				else
				{
					PointerConverter& pcvt = PointerConverter::get(l);
					return pcvt.get_const_pointer<T>(objwrapper);
				}
			}
		}
		return 0;
	}



	template<class T>
	standard::shared_ptr<T> get_shared_pointer(lua_State* l, int index, types::typetag<T> tag)
	{

		ObjectWrapperBase* objwrapper = object_wrapper(l, index);
		if (objwrapper)
		{
			if (static_cast<ObjectWrapperBase*>(objwrapper)->is_native_type(metatableType<standard::shared_ptr<T> >()))
			{
				standard::shared_ptr<T>* ptr = static_cast<standard::shared_ptr<T>*>(objwrapper->native_get());
				if (ptr) { return *ptr; }
			}
			else
			{
				PointerConverter& pcvt = PointerConverter::get(l);
				return pcvt.get_shared_pointer<T>(objwrapper);
			}
		}
		return standard::shared_ptr<T>();
	}
}

namespace kaguya
{
	//default implements
	template<typename T, typename Enable>
	bool lua_type_traits<T, Enable>::checkType(lua_State* l, int index)
	{
		return object_wrapper<T>(l, index) != 0;
	}
	template<typename T, typename Enable>
	bool lua_type_traits<T, Enable>::strictCheckType(lua_State* l, int index)
	{
		return object_wrapper<T>(l, index, false) != 0;
	}
	template<typename T, typename Enable>
	typename lua_type_traits<T, Enable>::get_type lua_type_traits<T, Enable>::get(lua_State* l, int index)
	{
		const typename traits::remove_reference<T>::type* pointer = get_const_pointer(l, index, types::typetag<typename traits::remove_reference<T>::type>());
		if (!pointer)
		{
			throw LuaTypeMismatch("type mismatch!!");
		}
		return *pointer;
	}
	template<typename T, typename Enable>
	int lua_type_traits<T, Enable>::push(lua_State* l, push_type v)
	{
		typedef ObjectWrapper<typename traits::remove_const_and_reference<T>::type> wrapper_type;
		void *storage = lua_newuserdata(l, sizeof(wrapper_type));
		new(storage) wrapper_type(v);
		class_userdata::setmetatable<T>(l);
		return 1;
	}

#if KAGUYA_USE_RVALUE_REFERENCE

	template<typename T, typename Enable>
	int lua_type_traits<T, Enable>::push(lua_State* l, NCRT&& v)
	{
		typedef ObjectWrapper<typename traits::remove_const_and_reference<T>::type> wrapper_type;
		void *storage = lua_newuserdata(l, sizeof(wrapper_type));
		new(storage) wrapper_type(std::forward<NCRT>(v));
		class_userdata::setmetatable<T>(l);
		return 1;
	}
#endif

	template<typename T> struct lua_type_traits<T
		, typename traits::enable_if<traits::is_const_reference<T>::value>::type> :lua_type_traits<typename traits::remove_const_reference<T>::type > {};



	template<typename REF> struct lua_type_traits < REF
		, typename traits::enable_if<traits::is_lvalue_reference<REF>::value && !traits::is_const<typename traits::remove_reference<REF>::type>::value>::type >
	{
		typedef void Registerable;

		typedef REF get_type;
		typedef REF push_type;
		typedef typename traits::remove_reference<REF>::type T;

		static bool strictCheckType(lua_State* l, int index)
		{
			return object_wrapper<T>(l, index, false) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			if (lua_type(l, index) == LUA_TLIGHTUSERDATA)
			{
				return true;
			}
			return object_wrapper<T>(l, index) != 0;
		}
		static get_type get(lua_State* l, int index)
		{
			T* pointer = get_pointer(l, index, types::typetag<T>());
			if (!pointer)
			{
				throw LuaTypeMismatch("type mismatch!!");
			}
			return *pointer;
		}
		static int push(lua_State* l, push_type v)
		{
			if (!available_metatable<T>(l))
			{
				lua_pushlightuserdata(l, const_cast<typename traits::remove_const<T>::type*>(&v));
			}
			else
			{
				typedef ObjectPointerWrapper<T> wrapper_type;
				void *storage = lua_newuserdata(l, sizeof(wrapper_type));
				new(storage) wrapper_type(&v);
				class_userdata::setmetatable<T>(l);
			}
			return 1;
		}
	};

	template<typename PTR> struct lua_type_traits < PTR
		, typename traits::enable_if<traits::is_pointer<typename traits::remove_const_reference<PTR>::type>::value && !traits::is_function<typename traits::remove_pointer<PTR>::type>::value>::type >
	{
		typedef void Registerable;

		typedef PTR get_type;
		typedef PTR push_type;
		typedef typename traits::remove_pointer<PTR>::type T;

		static bool strictCheckType(lua_State* l, int index)
		{
			return object_wrapper<T>(l, index, false) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			int type = lua_type(l, index);
			if (type == LUA_TLIGHTUSERDATA
				|| type == LUA_TNIL
				|| type == LUA_TNONE
				|| (type == LUA_TNUMBER && lua_tonumber(l, index) == 0)) //allow zero for nullptr;
			{
				return true;
			}
			return object_wrapper<T>(l, index) != 0;
		}
		static get_type get(lua_State* l, int index)
		{
			int type = lua_type(l, index);
			if (type == LUA_TUSERDATA || type == LUA_TLIGHTUSERDATA)
			{
				if (traits::is_const<T>::value)
				{
					return const_cast<get_type>(get_const_pointer(l, index, types::typetag<T>()));
				}
				else
				{
					return get_pointer(l, index, types::typetag<T>());
				}
			}

			if (type == LUA_TNIL
				|| type == LUA_TNONE
				|| (type == LUA_TNUMBER && lua_tonumber(l, index) == 0)) //allow zero for nullptr;
			{
				return 0;
			}
			throw LuaTypeMismatch("type mismatch!!");
			return 0;
		}
		static int push(lua_State* l, push_type v)
		{
			if (!v)
			{
				lua_pushnil(l);
			}
			else if (!available_metatable<T>(l))
			{
				lua_pushlightuserdata(l, const_cast<typename traits::remove_const<T>::type*>(v));
			}
			else
			{
				typedef ObjectPointerWrapper<T> wrapper_type;
				void *storage = lua_newuserdata(l, sizeof(wrapper_type));
				new(storage) wrapper_type(v);
				class_userdata::setmetatable<T>(l);
			}
			return 1;
		}
	};

	///! traits for bool
	template<>	struct lua_type_traits<bool> {
		typedef bool get_type;
		typedef bool push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TBOOLEAN;
		}
		static bool checkType(lua_State* l, int index)
		{
			return true;
		}
		static bool get(lua_State* l, int index)
		{
			return lua_toboolean(l, index) != 0;
		}
		static int push(lua_State* l, bool s)
		{
			lua_pushboolean(l, s);
			return 1;
		}
	};

	template<>	struct lua_type_traits<void> {
		typedef void* get_type;
		typedef void* push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return true;
		}
		static bool checkType(lua_State* l, int index)
		{
			return true;
		}
		static get_type get(lua_State* l, int index)
		{
			return 0;
		}
		static int push(lua_State* l, push_type s)
		{
			return 0;
		}
	};


	///! traits for reference_wrapper
	template<typename T> struct lua_type_traits<standard::reference_wrapper<T> > {
		typedef const standard::reference_wrapper<T>& push_type;

		static int push(lua_State* l, push_type v)
		{
			return lua_type_traits<T*>::push(l, &v.get());
		}
	};

	///! traits for shared_ptr
	template<typename T> struct lua_type_traits<standard::shared_ptr<T> > {
		typedef const standard::shared_ptr<T>& push_type;
		typedef standard::shared_ptr<T> get_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return object_wrapper<get_type>(l, index, false) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			return object_wrapper<get_type>(l, index) != 0 ||
				lua_isnil(l, index);
		}
		static get_type get(lua_State* l, int index)
		{
			if (lua_isnil(l, index)) {
				return get_type();
			}
			get_type pointer = get_shared_pointer<T>(l, index, types::typetag<T>());
			return pointer;
		}

		static int push(lua_State* l, push_type v)
		{
			if (v)
			{
				typedef ObjectSharedPointerWrapper wrapper_type;
				void *storage = lua_newuserdata(l, sizeof(wrapper_type));
				new(storage) wrapper_type(v);
				class_userdata::setmetatable<T>(l);
			}
			else
			{
				lua_pushnil(l);
			}
			return 1;
		}
	};
	template<>
	struct lua_type_traits<standard::shared_ptr<void> > {
		typedef const standard::shared_ptr<void>& push_type;
		typedef standard::shared_ptr<void> get_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return object_wrapper<get_type>(l, index, false) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			return object_wrapper<get_type>(l, index) != 0 ||
				lua_isnil(l, index);
		}
		static get_type get(lua_State* l, int index)
		{
			if (lua_isnil(l, index)) {
				return get_type();
			}
			return get_shared_pointer(l, index, types::typetag<void>());
		}

		static int push(lua_State* l, push_type v)
		{
			if (v)
			{
				typedef ObjectSharedPointerWrapper wrapper_type;
				void *storage = lua_newuserdata(l, sizeof(wrapper_type));
				new(storage) wrapper_type(v);
			}
			else
			{
				lua_pushnil(l);
			}
			return 1;
		}
	};

#if KAGUYA_USE_CPP11
	///! traits for unique_ptr
	template<typename T, typename Deleter> struct lua_type_traits<std::unique_ptr<T, Deleter> > {
		typedef std::unique_ptr<T, Deleter>&& push_type;
		typedef std::unique_ptr<T, Deleter> get_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return object_wrapper(l, index, metatableName<get_type>(), false) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			return object_wrapper(l, index, metatableName<get_type>()) != 0 ||
				lua_isnil(l, index);
		}
		static get_type get(lua_State* l, int index)
		{
			if (lua_isnoneornil(l, index)) {
				return 0;
			}
			const get_type* pointer = get_const_pointer(l, index, types::typetag<get_type>());
			if (!pointer)
			{
				throw LuaTypeMismatch("type mismatch!!");
			}
			return *pointer;
		}

		static int push(lua_State* l, push_type v)
		{
			if (v)
			{
				typedef ObjectSmartPointerWrapper<std::unique_ptr<T, Deleter> > wrapper_type;
				void *storage = lua_newuserdata(l, sizeof(wrapper_type));
				new(storage) wrapper_type(std::forward<push_type>(v));
				class_userdata::setmetatable<T>(l);
			}
			else
			{
				lua_pushnil(l);
			}
			return 1;
			return 1;
		}
	};
	///! traits for nullptr
	template<>	struct lua_type_traits<std::nullptr_t> {
		typedef const std::nullptr_t& push_type;
		typedef std::nullptr_t get_type;

		static bool checkType(lua_State* l, int index)
		{
			return lua_isnoneornil(l, index);
		}
		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_isnil(l, index);
		}
		static get_type get(lua_State* l, int index)
		{
			if (!lua_isnoneornil(l, index)) {
				throw LuaTypeMismatch("type mismatch!!");
			}
			return nullptr;
		}

		static int push(lua_State* l, const std::nullptr_t& v)
		{
			lua_pushnil(l);
			return 1;
		}
	};
#endif

	///! traits for 
	template<>	struct lua_type_traits<ObjectWrapperBase*> {
		typedef ObjectWrapperBase* get_type;
		typedef ObjectWrapperBase* push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return object_wrapper(l, index) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			return object_wrapper(l, index) != 0;
		}
		static get_type get(lua_State* l, int index)
		{
			return object_wrapper(l, index);
		}
	};

	///! traits for native type of Luathread
	template<>	struct lua_type_traits<lua_State*> {
		typedef lua_State* get_type;
		typedef lua_State* push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_isthread(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isthread(l, index);
		}
		static lua_State* get(lua_State* l, int index)
		{
			return lua_tothread(l, index);
		}
	};

	template<typename T> struct lua_type_traits < T
		, typename traits::enable_if<traits::is_floating_point<T>::value>::type >
	{
		typedef typename traits::remove_const_reference<T>::type get_type;
		typedef lua_Number push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TNUMBER;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isnumber(l, index) != 0;
		}
		static get_type get(lua_State* l, int index)
		{
			return static_cast<T>(lua_tonumber(l, index));
		}
		static int push(lua_State* l, lua_Number s)
		{
			lua_pushnumber(l, s);
			return 1;
		}
	};

	template<typename T> struct lua_type_traits<T
		, typename traits::enable_if<traits::is_integral<T>::value>::type>
	{
		typedef typename traits::remove_const_reference<T>::type get_type;
#if LUA_VERSION_NUM >= 503
		typedef lua_Integer push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_isinteger(l, index) != 0;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isnumber(l, index) != 0;
		}
		static get_type get(lua_State* l, int index)
		{
			return static_cast<T>(lua_tointeger(l, index));
		}
		static int push(lua_State* l, lua_Integer s)
		{
			lua_pushinteger(l, s);
			return 1;
		}
#else
		typedef typename lua_type_traits<lua_Number>::push_type push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type_traits<lua_Number>::strictCheckType(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_type_traits<lua_Number>::checkType(l, index);
		}
		static get_type get(lua_State* l, int index)
		{
			return static_cast<get_type>(lua_type_traits<lua_Number>::get(l, index));
		}
		static int push(lua_State* l, push_type s)
		{
			return lua_type_traits<lua_Number>::push(l, s);
		}
#endif
	};

	template<typename T> struct lua_type_traits<T
		, typename traits::enable_if<traits::is_enum<T>::value>::type>
	{
		typedef typename traits::remove_const_reference<T>::type get_type;
		typedef typename traits::remove_const_reference<T>::type push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type_traits<luaInt>::strictCheckType(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_type_traits<luaInt>::checkType(l, index);
		}
		static get_type get(lua_State* l, int index)
		{
			return static_cast<get_type>(lua_type_traits<luaInt>::get(l, index));
		}
		static int push(lua_State* l, push_type s)
		{
			return lua_type_traits<luaInt>::push(l, static_cast<typename lua_type_traits<int64_t>::push_type>(s));
		}
	};


	template<>	struct lua_type_traits<const char*> {
		typedef std::string get_type;
		typedef const char* push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TSTRING;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isstring(l, index) != 0;
		}
		static const char* get(lua_State* l, int index)
		{
			return lua_tostring(l, index);
		}
		static int push(lua_State* l, const char* s)
		{
			lua_pushstring(l, s);
			return 1;
		}
	};

	template<int N>	struct lua_type_traits<char[N]> {
		typedef std::string get_type;
		typedef const char* push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TSTRING;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isstring(l, index) != 0;
		}
		static const char* get(lua_State* l, int index)
		{
			return lua_tostring(l, index);
		}
		static int push(lua_State* l, const char s[N])
		{
			lua_pushstring(l, s);
			return 1;
		}
	};
	template<int N>	struct lua_type_traits<const char[N]> :lua_type_traits<const char*> {};

	template<>	struct lua_type_traits<std::string> {
		typedef std::string get_type;
		typedef const std::string& push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TSTRING;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isstring(l, index) != 0;
		}
		static get_type get(lua_State* l, int index)
		{
			size_t size = 0;
			const char* buffer = lua_tolstring(l, index, &size);
			return std::string(buffer, size);
		}
		static int push(lua_State* l, const std::string& s)
		{
			lua_pushlstring(l, s.c_str(), s.size());
			return 1;
		}
	};

//generated header by push_tuple_generator.py
template<>
struct lua_type_traits<standard::tuple<> >
{
  static int push(lua_State* l, const standard::tuple<>& v)
  {
    int count =0;
    return count;
  }
};
template<typename T1>
struct lua_type_traits<standard::tuple<T1> >
{
  static int push(lua_State* l, const standard::tuple<T1>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    return count;
  }
};
template<typename T1,typename T2>
struct lua_type_traits<standard::tuple<T1,T2> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3>
struct lua_type_traits<standard::tuple<T1,T2,T3> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3,typename T4>
struct lua_type_traits<standard::tuple<T1,T2,T3,T4> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3,T4>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    count += lua_type_traits<T4>::push(l,standard::get<3>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3,typename T4,typename T5>
struct lua_type_traits<standard::tuple<T1,T2,T3,T4,T5> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3,T4,T5>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    count += lua_type_traits<T4>::push(l,standard::get<3>(v));
    count += lua_type_traits<T5>::push(l,standard::get<4>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3,typename T4,typename T5,typename T6>
struct lua_type_traits<standard::tuple<T1,T2,T3,T4,T5,T6> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3,T4,T5,T6>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    count += lua_type_traits<T4>::push(l,standard::get<3>(v));
    count += lua_type_traits<T5>::push(l,standard::get<4>(v));
    count += lua_type_traits<T6>::push(l,standard::get<5>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3,typename T4,typename T5,typename T6,typename T7>
struct lua_type_traits<standard::tuple<T1,T2,T3,T4,T5,T6,T7> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3,T4,T5,T6,T7>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    count += lua_type_traits<T4>::push(l,standard::get<3>(v));
    count += lua_type_traits<T5>::push(l,standard::get<4>(v));
    count += lua_type_traits<T6>::push(l,standard::get<5>(v));
    count += lua_type_traits<T7>::push(l,standard::get<6>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3,typename T4,typename T5,typename T6,typename T7,typename T8>
struct lua_type_traits<standard::tuple<T1,T2,T3,T4,T5,T6,T7,T8> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3,T4,T5,T6,T7,T8>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    count += lua_type_traits<T4>::push(l,standard::get<3>(v));
    count += lua_type_traits<T5>::push(l,standard::get<4>(v));
    count += lua_type_traits<T6>::push(l,standard::get<5>(v));
    count += lua_type_traits<T7>::push(l,standard::get<6>(v));
    count += lua_type_traits<T8>::push(l,standard::get<7>(v));
    return count;
  }
};
template<typename T1,typename T2,typename T3,typename T4,typename T5,typename T6,typename T7,typename T8,typename T9>
struct lua_type_traits<standard::tuple<T1,T2,T3,T4,T5,T6,T7,T8,T9> >
{
  static int push(lua_State* l, const standard::tuple<T1,T2,T3,T4,T5,T6,T7,T8,T9>& v)
  {
    int count =0;
    count += lua_type_traits<T1>::push(l,standard::get<0>(v));
    count += lua_type_traits<T2>::push(l,standard::get<1>(v));
    count += lua_type_traits<T3>::push(l,standard::get<2>(v));
    count += lua_type_traits<T4>::push(l,standard::get<3>(v));
    count += lua_type_traits<T5>::push(l,standard::get<4>(v));
    count += lua_type_traits<T6>::push(l,standard::get<5>(v));
    count += lua_type_traits<T7>::push(l,standard::get<6>(v));
    count += lua_type_traits<T8>::push(l,standard::get<7>(v));
    count += lua_type_traits<T9>::push(l,standard::get<8>(v));
    return count;
  }
};

	struct NewTable {
		NewTable() :reserve_array_(0), reserve_record_(0) {}
		NewTable(int reserve_array, int reserve_record_) :reserve_array_(reserve_array), reserve_record_(reserve_record_) {}
		int reserve_array_;
		int reserve_record_;
	};
	struct NewThread {};
	struct GlobalTable {};
	struct NilValue {};

	struct NoTypeCheck {};

	template<>	struct lua_type_traits<NewTable> {
		static int push(lua_State* l, const NewTable& table)
		{
			lua_createtable(l, table.reserve_array_, table.reserve_record_);
			return 1;
		}
	};
	template<>	struct lua_type_traits<NewThread> {
		static int push(lua_State* l, const NewThread&)
		{
			lua_newthread(l);
			return 1;
		}
	};
	template<>	struct lua_type_traits<NilValue> {
		static int push(lua_State* l, const NilValue&)
		{
			lua_pushnil(l);
			return 1;
		}
	};
	template<>	struct lua_type_traits<GlobalTable> {
		static int push(lua_State* l, const GlobalTable&)
		{
#if LUA_VERSION_NUM >= 502
			lua_pushglobaltable(l);
#else
			lua_pushvalue(l, LUA_GLOBALSINDEX);
#endif
			return 1;
		}
	};



	template<typename Derived>
	class LuaBasicTypeFunctions
	{
	public:
		enum value_type
		{
			TYPE_NIL = LUA_TNIL,//!< nil type
			TYPE_BOOL = LUA_TBOOLEAN,//!< boolean type
			TYPE_LIGHTUSERDATA = LUA_TLIGHTUSERDATA,//!< light userdata type
			TYPE_NUMBER = LUA_TNUMBER,//!< number type
			TYPE_STRING = LUA_TSTRING,//!< string type
			TYPE_TABLE = LUA_TTABLE,//!< table type
			TYPE_FUNCTION = LUA_TFUNCTION,//!< function type
			TYPE_USERDATA = LUA_TUSERDATA,//!< userdata type
			TYPE_THREAD = LUA_TTHREAD//!< thread(coroutine) type
		};

		bool isNilref_()const {
			int t = type();
			return t == LUA_TNIL || t == LUA_TNONE;
		}

		typedef void (LuaBasicTypeFunctions::*bool_type)() const;
		void this_type_does_not_support_comparisons() const {}

		/**
		* @brief Equivalent to `#` operator for strings and tables with no metamethods.
		* Follows Lua's reference manual documentation of `lua_rawlen`, ie. types other
		* than tables, strings or userdatas return 0.
		* @return Size of table, string length or userdata memory block size.
		*/
		size_t size() const {
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			int index = pushStackIndex_(state);
#if LUA_VERSION_NUM >= 502
			return lua_rawlen(state, index);
#else

			int type = lua_type(state, index);
			if (type != TYPE_STRING && type != TYPE_TABLE && type != TYPE_USERDATA && type != TYPE_LIGHTUSERDATA)
			{
				return 0;
			}
			return lua_objlen(state, index);
#endif
	}

		//return type
		int type() const
		{
			lua_State* state = state_();
			if (!state)
			{
				return LUA_TNONE;
			}
			util::ScopedSavedStack save(state);
			return lua_type(state, pushStackIndex_(state));
		}

		//return type name
		const char* typeName()const
		{
			return lua_typename(state_(), type());
		}


		template<typename T>typename lua_type_traits<T>::get_type get()const
		{
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			return lua_type_traits<T>::get(state, pushStackIndex_(state));
		}
		template<typename T>
		typename lua_type_traits<T>::get_type get(bool& was_valid, bool allow_convertible = true)const
		{
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			int stackindex = pushStackIndex_(state);
			if (allow_convertible)
			{
				was_valid = lua_type_traits<T>::checkType(state, stackindex);
			}
			else
			{
				was_valid = lua_type_traits<T>::strictCheckType(state, stackindex);
			}
			if (was_valid)
			{
				return lua_type_traits<T>::get(state, stackindex);
			}
			else
			{
				return T();
			}
		}
		template<typename T>
		operator T()const
		{
			return get<T>();
		}

		operator bool_type() const
		{
			return (!isNilref_() && get<bool>() == true) ? &LuaBasicTypeFunctions::this_type_does_not_support_comparisons : 0;
		}


		/**
		* @name relational operators
		* @brief
		*/
		//@{
		template<typename OtherDrived>
		inline bool operator==(const LuaBasicTypeFunctions<OtherDrived>& rhs)const
		{
			if (isNilref_() || rhs.isNilref_()) { return !isNilref_() == !rhs.isNilref_(); }
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			int index = pushStackIndex_(state);
			int rhsindex = rhs.pushStackIndex_(state);
#if LUA_VERSION_NUM >= 502
			return lua_compare(state, index, rhsindex, LUA_OPEQ) != 0;
#else
			return lua_equal(state, index, rhsindex) != 0;
#endif
		}
		template<typename OtherDrived>
		inline bool operator<(const LuaBasicTypeFunctions<OtherDrived>& rhs)const
		{
			if (isNilref_() || rhs.isNilref_()) { return !isNilref_() != !rhs.isNilref_(); }
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			int index = pushStackIndex_(state);
			int rhsindex = rhs.pushStackIndex_(state);
#if LUA_VERSION_NUM >= 502
			return lua_compare(state, index, rhsindex, LUA_OPLT) != 0;
#else
			return lua_lessthan(state, index, rhsindex) != 0;
#endif
		}
		template<typename OtherDrived>
		inline bool operator<=(const LuaBasicTypeFunctions<OtherDrived>& rhs)const
		{
			if (isNilref_() || rhs.isNilref_()) { return !isNilref_() == !rhs.isNilref_(); }
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			int index = pushStackIndex_(state);
			int rhsindex = rhs.pushStackIndex_(state);
#if LUA_VERSION_NUM >= 502
			return lua_compare(state, index, rhsindex, LUA_OPLE) != 0;
#else
			return lua_equal(state, index, rhsindex) != 0 || lua_lessthan(state, index, rhsindex) != 0;
#endif
		}
		template<typename OtherDrived>
		inline bool operator>=(const LuaBasicTypeFunctions<OtherDrived>& rhs)const
		{
			return rhs <= (*this);
		}
		template<typename OtherDrived>
		inline bool operator>(const LuaBasicTypeFunctions<OtherDrived>& rhs)const
		{
			return rhs < (*this);
		}
		template<typename OtherDrived>
		inline bool operator!=(const LuaBasicTypeFunctions<OtherDrived>& rhs)const
		{
			return !this->operator==(rhs);
		}

		template<typename T>
		inline typename traits::enable_if<!traits::is_convertible<T*, LuaBasicTypeFunctions<T>*>::value, bool>::type operator==(const T& rhs)const
		{
			try
			{
				return get<T>() == rhs;
			}
			catch (const LuaTypeMismatch&)
			{
				return false;
			}
			return false;
		}
		template<typename T>
		inline typename traits::enable_if<!traits::is_convertible<T*, LuaBasicTypeFunctions<T>*>::value, bool>::type operator!=(const T& rhs)const
		{
			return !((*this) == rhs);
		}
		//@}

		void dump(std::ostream& os)const
		{
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			util::stackValueDump(os, state, stackIndex);
		}
		lua_State* state_()const
		{
			return static_cast<const Derived*>(this)->state();
		}
		int pushStackIndex_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->pushStackIndex(state);
		}
	private:
};



	template<typename D>
	inline std::ostream& operator<<(std::ostream& os, const LuaBasicTypeFunctions<D>& ref)
	{
		ref.dump(os);
		return os;
	}



	/**
	* @name relational operators
	* @brief
	*/
	//@{

#define KAGUYA_ENABLE_IF_NOT_LUAREF(RETTYPE) typename traits::enable_if<!traits::is_convertible<T*, LuaBasicTypeFunctions<T>*>::value, RETTYPE>::type
	template<typename D, typename T>
	inline KAGUYA_ENABLE_IF_NOT_LUAREF(bool) operator == (const T& lhs, const LuaBasicTypeFunctions<D>& rhs)
	{
		return rhs == lhs;
	}
	template<typename D, typename T>
	inline KAGUYA_ENABLE_IF_NOT_LUAREF(bool) operator != (const T& lhs, const LuaBasicTypeFunctions<D>& rhs)
	{
		return !(rhs == lhs);
	}
	//@}
}


#define KAGUYA_ERROR_HANDLER_METATABLE "error_handler_kaguya_metatype"

namespace kaguya
{

	inline const char* get_error_message(lua_State *state)
	{
		const char* message = lua_tostring(state, -1);
		return message ? message : "unknown error";
	}



#if KAGUYA_NO_SET_AT_PANIC
	inline void set_at_panic_handler(lua_State* state)
	{
	}
	inline int lua_pcall_wrap(lua_State* state, int argnum, int retnum)
	{
		int result = lua_pcall(state, argnum, retnum, 0);
		return result;
	}
#else
	inline int panic_handler(lua_State *state)
	{
		const char* errormessage = get_error_message(state);
		throw LuaException(lua_status(state), errormessage);
		return 0;
	}
	inline void set_at_panic_handler(lua_State* state)
	{
		lua_atpanic(state, panic_handler);
	}
	inline int lua_pcall_wrap(lua_State* state, int argnum, int retnum)
	{
		try
		{
			lua_call(state, argnum, LUA_MULTRET);
		}
		catch (const LuaException& e)
		{
			return LUA_ERRRUN;
//			return e.status();//status not changed at luajit 2.0.4
		}
		return lua_status(state);
	}
#endif

	struct ErrorHandler
	{
		typedef standard::function<void(int, const char*)> function_type;


		static void handle(const char* message, lua_State *state)
		{
			function_type handler = getHandler(state);
			if (handler)
			{
				handler(0, message);
			}
		}
		static void handle(int status_code, const char* message, lua_State *state)
		{
			function_type handler = getHandler(state);
			if (handler)
			{
				handler(status_code, message);
			}
		}
		static void handle(int status_code, lua_State *state)
		{
			function_type handler = getHandler(state);
			if (handler)
			{
				handler(status_code, get_error_message(state));
			}
		}

		static function_type getHandler(lua_State* state)
		{

			function_type* funptr = getFunctionPointer(state);
			if (funptr)
			{
				return *funptr;
			}
			return function_type();
		}


		static void unregisterHandler(lua_State* state)
		{
			if (state)
			{
				function_type* funptr = getFunctionPointer(state);
				if (funptr)
				{
					*funptr = function_type();
				}
			}
		}
		static void registerHandler(lua_State* state, function_type f)
		{
			
			set_at_panic_handler(state);
			if (state)
			{
				util::ScopedSavedStack save(state);
				lua_pushlightuserdata(state, handlerRegistryKey());
				if (class_userdata::newmetatable<function_type>(state))//register error handler destructor to Lua state
				{
					lua_pushcclosure(state, &error_handler_cleanner, 0);
					lua_setfield(state, -2, "__gc");
					lua_setfield(state, -1, "__index");
					void* ptr = lua_newuserdata(state, sizeof(function_type));//dummy data for gc call
					if (!ptr) { throw std::runtime_error("critical error. maybe failed memory allocation"); }//critical error
					function_type* funptr = new(ptr) function_type();
					if (!funptr) { throw std::runtime_error("critical error. maybe failed memory allocation"); }//critical error
					class_userdata::setmetatable<function_type>(state);
					lua_settable(state, LUA_REGISTRYINDEX);
					*funptr = f;
				}
				else
				{
					function_type* funptr = getFunctionPointer(state);
					if (funptr)
					{
						*funptr = f;
					}
				}
			}
		}
		
	private:

		static void* handlerRegistryKey()
		{
			static void* key;
			return key;
		}
		static function_type* getFunctionPointer(lua_State* state)
		{
			if (state)
			{
				util::ScopedSavedStack save(state);
				lua_pushlightuserdata(state, handlerRegistryKey());
				lua_gettable(state, LUA_REGISTRYINDEX);
				function_type* ptr = (function_type*)lua_touserdata(state, -1);
				return ptr;
			}
			return 0;
		}

		ErrorHandler() {}

		ErrorHandler(const ErrorHandler&);
		ErrorHandler& operator=(const ErrorHandler&);

		static int error_handler_cleanner(lua_State *state)
		{
			function_type* ptr = (function_type*)lua_touserdata(state, 1);
			ptr->~function_type();
			return 0;
		}
	};

	namespace except
	{
		inline void OtherError(lua_State *state, const std::string& message)
		{
			ErrorHandler::handle(message.c_str(), state);
#if !KAGUYA_ERROR_NO_THROW
			throw LuaKaguyaError(message);
#endif
		}
		inline void typeMismatchError(lua_State *state, const std::string& message)
		{
			ErrorHandler::handle(message.c_str(), state);
#if !KAGUYA_ERROR_NO_THROW
			throw LuaTypeMismatch(message);
#endif
		}
		inline bool checkErrorAndThrow(int status, lua_State *state)
		{
			if (status != 0 && status != LUA_YIELD)
			{
				ErrorHandler::handle(status, state);
#if !KAGUYA_ERROR_NO_THROW
				const char* message = 0;
				switch (status)
				{
				case LUA_ERRSYNTAX:
					message = lua_tostring(state, -1);
					throw LuaSyntaxError(status, message ? std::string(message) : "unknown syntax error");
				case LUA_ERRRUN:
					message = lua_tostring(state, -1);
					throw LuaRuntimeError(status, message ? std::string(message) : "unknown runtime error");
				case LUA_ERRMEM:
					throw LuaMemoryError(status, "lua memory allocation error");
				case LUA_ERRERR:
					message = lua_tostring(state, -1);
					throw LuaRunningError(status, message ? std::string(message) : "unknown error");
				case LUA_ERRGCMM:
					message = lua_tostring(state, -1);
					throw LuaGCError(status, message ? std::string(message) : "unknown gc error");
				default:
					throw LuaUnknownError(status, "lua unknown error");
				}
#endif
				return false;
			}
			return true;
		}
	}
}

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <vector>
#include <set>
#include <map>
#include <cassert>
#include <algorithm>
#include <ostream>


namespace kaguya
{
	struct StackTop {};
	namespace Ref
	{
		struct NoMainCheck {};



		class StackRef
		{
		protected:
			static int to_absindex(lua_State *state, int idx) {
				return (idx > 0 || (idx <= LUA_REGISTRYINDEX))
					? idx
					: lua_gettop(state) + 1 + (idx);
			}
			lua_State *state_;
			int stack_index_;
			mutable bool pop_;
#if KAGUYA_USE_CPP11
			StackRef(StackRef&& src) :state_(src.state_), stack_index_(src.stack_index_), pop_(src.pop_)
			{
				src.pop_ = false;
			}
#else
			StackRef(const StackRef&src) : state_(src.state_), stack_index_(src.stack_index_), pop_(src.pop_)
			{
				src.pop_ = false;
			}
#endif
			StackRef(lua_State* s, int index) :state_(s), stack_index_(to_absindex(s, index)), pop_(true)
			{
			}
			StackRef(lua_State* s, int index, bool pop) :state_(s), stack_index_(to_absindex(s, index)), pop_(pop)
			{
			}
			~StackRef()
			{
				if (state_ && pop_)
				{
					lua_settop(state_, stack_index_ - 1);
				}
			}
		public:


			bool isNilref()const { return state_ == 0 || lua_type(state_, stack_index_) == LUA_TNIL; }

			int push()const
			{
				lua_pushvalue(state_, stack_index_);
				return 1;
			}
			int push(lua_State* state)const
			{
				lua_pushvalue(state_, stack_index_);
				if (state_ != state)
				{
					lua_pushvalue(state_, stack_index_);
					lua_xmove(state_, state, 1);
				}
				return 1;
			}

			int pushStackIndex(lua_State* state)const
			{
				if (state_ != state)
				{
					lua_pushvalue(state_, stack_index_);
					lua_xmove(state_, state, 1);
					return lua_gettop(state);
				}
				else
				{
					return stack_index_;
				}
			}
			lua_State *state()const { return state_; }
			};

		class RegistoryRef
		{
			typedef void (RegistoryRef::*bool_type)() const;
			void this_type_does_not_support_comparisons() const {}


		public:

			RegistoryRef(const RegistoryRef& src) :state_(src.state_)
			{
				if (!src.isNilref())
				{
					src.push(state_);
					ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
				}
				else
				{
					ref_ = LUA_REFNIL;
				}
			}
			RegistoryRef& operator =(const RegistoryRef& src)
			{
				unref();
				state_ = src.state_;
				if (!src.isNilref())
				{
					src.push(state_);
					ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
				}
				else
				{
					ref_ = LUA_REFNIL;
				}
				return *this;
			}
#if KAGUYA_USE_RVALUE_REFERENCE
			RegistoryRef(RegistoryRef&& src)throw() :state_(0), ref_(LUA_REFNIL)
			{
				swap(src);
			}
			RegistoryRef& operator =(RegistoryRef&& src)throw()
			{
				swap(src);
				return *this;
			}
#endif

			RegistoryRef() :state_(0), ref_(LUA_REFNIL) {}
			RegistoryRef(lua_State* state) :state_(state), ref_(LUA_REFNIL) {}


			RegistoryRef(lua_State* state, StackTop, NoMainCheck) :state_(state), ref_(LUA_REFNIL)
			{
				if (!state_) { return; }
				ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
			}

			RegistoryRef(lua_State* state, StackTop) :state_(state), ref_(LUA_REFNIL)
			{
				if (!state_) { return; }
				ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
				state_ = util::toMainThread(state_);
			}

			void swap(RegistoryRef& other)throw()
			{
				std::swap(state_, other.state_);
				std::swap(ref_, other.ref_);
			}

			template<typename T>
			RegistoryRef(lua_State* state, const T& v, NoMainCheck) : state_(state), ref_(LUA_REFNIL)
			{
				if (!state_) { return; }
				util::ScopedSavedStack save(state_);
				util::one_push(state_, v);
				ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
			}
			template<typename T>
			RegistoryRef(lua_State* state, const T& v) : state_(state), ref_(LUA_REFNIL)
			{
				if (!state_) { return; }
				util::ScopedSavedStack save(state_);
				util::one_push(state_, v);
				ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
				state_ = util::toMainThread(state_);
			}
#if KAGUYA_USE_CPP11
			template<typename T>
			RegistoryRef(lua_State* state, T&& v, NoMainCheck) : state_(state), ref_(LUA_REFNIL)
			{
				if (!state_) { return; }
				util::ScopedSavedStack save(state_);
				util::one_push(state_, standard::forward<T>(v));
				ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
			}
			template<typename T>
			RegistoryRef(lua_State* state, T&& v) : state_(state), ref_(LUA_REFNIL)
			{
				if (!state_) { return; }
				util::ScopedSavedStack save(state_);
				util::one_push(state_, standard::forward<T>(v));
				ref_ = luaL_ref(state_, LUA_REGISTRYINDEX);
				state_ = util::toMainThread(state_);
			}
#endif
			~RegistoryRef()
			{
				unref();
			}

			//push to Lua stack
			int push()const
			{
				return push(state_);
			}
			int push(lua_State* state)const
			{
				if (isNilref())
				{
					lua_pushnil(state);
					return 1;
				}
#if LUA_VERSION_NUM >= 502
				if (state != state_)
				{//state check
					assert(util::toMainThread(state) == util::toMainThread(state_));
				}
#endif
				lua_rawgeti(state, LUA_REGISTRYINDEX, ref_);
				return 1;
			}

			int pushStackIndex(lua_State* state)const
			{
				push(state);
				return lua_gettop(state);
			}
			lua_State *state()const { return state_; }

			bool isNilref()const { return state_ == 0 || ref_ == LUA_REFNIL; }

		protected:
			lua_State *state_;
			int ref_;


			void unref()
			{
				if (!isNilref())
				{
					luaL_unref(state_, LUA_REGISTRYINDEX, ref_);
					state_ = 0;
					ref_ = LUA_REFNIL;
				}
			}
		};

		}
	}
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <vector>
#include <set>
#include <map>
#include <cassert>
#include <algorithm>
#include <ostream>


namespace kaguya
{
	class LuaTable;

    class FunctionResults;

	class FunctionResultProxy
	{
	public:
		template<typename RetType>
		static RetType ReturnValue(lua_State* state,int restatus, int retindex, types::typetag<RetType> tag);
		static FunctionResults ReturnValue(lua_State* state, int restatus, int retindex, types::typetag<FunctionResults> tag);
		static void ReturnValue(lua_State* state, int restatus, int retindex, types::typetag<void> tag);
	};



	template<typename Derived>
	class LuaFunctionImpl
	{
	private:
		lua_State* state_()const
		{
			return static_cast<const Derived*>(this)->state();
		}
		int pushStackIndex_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->pushStackIndex(state);
		}
		int push_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->push(state);
		}

	public:
		/**
		* @name function type
		*/
		//@{

		/**
		* set function environment table
		*/
		bool setFunctionEnv(const LuaTable& env);
		/**
		* set function environment to new table
		*/
		bool setFunctionEnv(NewTable env);
		/**
		* get function environment table
		*/
		LuaTable getFunctionEnv();

		//@}

#if KAGUYA_USE_CPP11
		template<class Result, class...Args> Result call(Args&&... args)
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return Result();
			}
			int argstart = lua_gettop(state) + 1;
			push_(state);
			int argnum = util::push_args(state, std::forward<Args>(args)...);
			int result = lua_pcall_wrap(state, argnum, LUA_MULTRET);
			except::checkErrorAndThrow(result, state);
			return FunctionResultProxy::ReturnValue(state, result, argstart, types::typetag<Result>());
		}

		template<class...Args> FunctionResults operator()(Args&&... args);
#else

#define KAGUYA_PP_TEMPLATE(N) ,KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)
#define KAGUYA_PUSH_ARG_DEF(N) ,KAGUYA_PP_CAT(a,N)
#define KAGUYA_CALL_DEF(N) \
		template<class Result KAGUYA_PP_REPEAT(N,KAGUYA_PP_TEMPLATE)>\
		Result call(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG))\
		{\
			lua_State* state = state_();\
			if (!state)\
			{\
				except::typeMismatchError(state, "is nil");\
				return Result();\
			}\
			int argstart = lua_gettop(state) + 1;\
			push_(state);\
			int argnum = util::push_args(state KAGUYA_PP_REPEAT(N,KAGUYA_PUSH_ARG_DEF));\
			int result = lua_pcall_wrap(state, argnum, LUA_MULTRET);\
			except::checkErrorAndThrow(result, state);\
			return FunctionResultProxy::ReturnValue(state,result, argstart, types::typetag<Result>());\
		}


		KAGUYA_CALL_DEF(0)
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_DEF)


#undef KAGUYA_PUSH_DEF
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_RESUME_DEF

#define KAGUYA_TEMPLATE_PARAMETER(N)
#define KAGUYA_FUNCTION_ARGS_DEF(N)
#define KAGUYA_CALL_ARGS(N)

#define KAGUYA_OP_FN_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline FunctionResults operator()(KAGUYA_FUNCTION_ARGS_DEF(N));

			KAGUYA_OP_FN_DEF(0)

#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_CALL_ARGS
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>
#define KAGUYA_FUNCTION_ARGS_DEF(N) KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG)
#define KAGUYA_CALL_ARGS(N) KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PUSH_ARG_DEF)

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PUSH_ARG_DEF(N) KAGUYA_PP_CAT(a,N) 

			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_OP_FN_DEF)
#undef KAGUYA_OP_FN_DEF
#undef KAGUYA_TEMPLATE_PARAMETER

#undef KAGUYA_CALL_ARGS
#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_CALL_DEF
#undef KAGUYA_OP_FN_DEF
			//@}
#endif


	};


	template<typename Derived>
	class LuaThreadImpl
	{
	private:
		lua_State* state_()const
		{
			return static_cast<const Derived*>(this)->state();
		}
		int pushStackIndex_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->pushStackIndex(state);
		}

	public:
#if KAGUYA_USE_CPP11
		template<class Result, class...Args> Result resume(Args&&... args)
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return Result();
			}
			util::ScopedSavedStack save(state);
			int corStackIndex = pushStackIndex_(state);
			lua_State* thread = lua_type_traits<lua_State*>::get(state, corStackIndex);
			int argstart = lua_gettop(thread) + 1;
			if (argstart > 1 && lua_status(thread) != LUA_YIELD)
			{
				argstart -= 1;
			}
			util::push_args(thread, std::forward<Args>(args)...);
			int argnum = lua_gettop(thread) - argstart;
			int result = util::lua_resume_compat(thread, argnum);
			except::checkErrorAndThrow(result, thread);
			return FunctionResultProxy::ReturnValue(thread, result, argstart, types::typetag<Result>());
		}
		template<class...Args> FunctionResults operator()(Args&&... args);
#else


#define KAGUYA_PP_TEMPLATE(N) ,KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)
#define KAGUYA_PUSH_ARG_DEF(N) ,KAGUYA_PP_CAT(a,N)
		
#define KAGUYA_RESUME_DEF(N) \
		template<class Result  KAGUYA_PP_REPEAT(N,KAGUYA_PP_TEMPLATE)>\
		Result resume(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG))\
		{\
			lua_State* state = state_();\
			if (!state)\
			{\
				except::typeMismatchError(state, "is nil");\
				return Result();\
			}\
			util::ScopedSavedStack save(state);\
			int corStackIndex = pushStackIndex_(state);\
			lua_State* thread = lua_type_traits<lua_State*>::get(state, corStackIndex);\
			int argstart = lua_gettop(thread) + 1;\
			if (argstart > 1 && lua_status(thread) != LUA_YIELD)\
			{\
				argstart -= 1;\
			}\
			util::push_args(thread KAGUYA_PP_REPEAT(N, KAGUYA_PUSH_ARG_DEF));\
			int argnum = lua_gettop(thread) - argstart;\
			int result = util::lua_resume_compat(thread, argnum);\
			except::checkErrorAndThrow(result, thread);\
			return FunctionResultProxy::ReturnValue(thread,result, argstart, types::typetag<Result>());\
		}

		KAGUYA_RESUME_DEF(0)
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_RESUME_DEF)

#undef KAGUYA_PUSH_DEF
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_RESUME_DEF

#define KAGUYA_TEMPLATE_PARAMETER(N)
#define KAGUYA_FUNCTION_ARGS_DEF(N)
#define KAGUYA_CALL_ARGS(N)

#define KAGUYA_OP_FN_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline FunctionResults operator()(KAGUYA_FUNCTION_ARGS_DEF(N));

			KAGUYA_OP_FN_DEF(0)

#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_CALL_ARGS
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>
#define KAGUYA_FUNCTION_ARGS_DEF(N) KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG)
#define KAGUYA_CALL_ARGS(N) KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PUSH_ARG_DEF)

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PUSH_ARG_DEF(N) KAGUYA_PP_CAT(a,N) 

			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_OP_FN_DEF)
#undef KAGUYA_OP_FN_DEF
#undef KAGUYA_TEMPLATE_PARAMETER

#undef KAGUYA_CALL_ARGS
#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_CALL_DEF
#undef KAGUYA_OP_FN_DEF
			//@}
#endif


		//@{
		/**
		* @return state status
		*/
			int threadStatus()const
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return LUA_ERRRUN;
			}
			util::ScopedSavedStack save(state);
			int corStackIndex = pushStackIndex_(state);
			lua_State* thread = lua_type_traits<lua_State*>::get(state, corStackIndex);

			if (!thread)
			{
				except::typeMismatchError(state, "is not thread");
				return LUA_ERRRUN;
			}
			return lua_status(thread);
		}

		//! deprecate
		int thread_status()const
		{
			return threadStatus();
		}

		/**
		* status of coroutine
		*/
		enum coroutine_status
		{
			COSTAT_RUNNING,//!< coroutine is running
			COSTAT_SUSPENDED,//!< coroutine is suspended
			COSTAT_NORMAL,//!<
			COSTAT_DEAD//!< coroutine is dead
		};

		/**
		* @return coroutine status
		*/
		coroutine_status costatus(lua_State *l = 0)const
		{
			lua_State* state = state_();
			if (!state)
			{
				return COSTAT_DEAD;
			}
			util::ScopedSavedStack save(state);
			int corStackIndex = pushStackIndex_(state);
			lua_State* thread = lua_type_traits<lua_State*>::get(state, corStackIndex);

			if (!thread)
			{
				except::typeMismatchError(state, "is not thread");
				return COSTAT_DEAD;
			}
			else if (thread == l)
			{
				return COSTAT_RUNNING;
			}
			else
			{
				switch (lua_status(thread))
				{
				case LUA_YIELD:
					return COSTAT_SUSPENDED;
				case 0://LUA_OK
				{
					if (lua_gettop(thread) == 0)
					{
						return COSTAT_DEAD;
					}
					else
					{
						return COSTAT_SUSPENDED;
					}
				}
				default:
					break;
				}
			}
			return COSTAT_DEAD;

		}

		/**
		* @return if coroutine status is dead, return true. Otherwise return false
		*/
		bool isThreadDead()const
		{
			return costatus() == COSTAT_DEAD;
		}
		//@}
	};

}// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <vector>
#include <set>
#include <map>
#include <cassert>
#include <algorithm>
#include <ostream>


namespace kaguya
{
	class LuaRef;
	class LuaStackRef;
	class LuaTable;
	template<typename KEY>
	class TableKeyReference;
	class mem_fun_binder;

	template<typename Derived>
	class LuaTableOrUserDataImpl
	{
	private:
		lua_State* state_()const
		{
			return static_cast<const Derived*>(this)->state();
		}
		int pushStackIndex_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->pushStackIndex(state);
		}
		int push_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->push(state);
		}

		template<typename K>
		struct gettablekey
		{
			typedef K key_type;
			typedef void value_type;
			std::vector<K>& v_;
			gettablekey(std::vector<K>&v) :v_(v) {}
			void operator ()(K key, const void*)
			{
				v_.push_back(key);
			}
		};
		template<typename V>
		struct gettablevalue
		{
			typedef void key_type;
			typedef V value_type;
			std::vector<V>& v_;
			gettablevalue(std::vector<V>&v) :v_(v) {}
			void operator ()(const void*, V value)
			{
				v_.push_back(value);
			}
		};
		template<typename K, typename V>
		struct gettablemap
		{
			typedef K key_type;
			typedef V value_type;
			std::map<K, V>& m_;
			gettablemap(std::map<K, V>& m) :m_(m) {}
			void operator ()(K key, V value)
			{
				m_[key] = value;
			}
		};


	public:

		bool setMetatable(const LuaTable& table);
		LuaTable getMetatable()const;


		/**
		* @brief table->*"function_name"() in c++ and table:function_name(); in lua is same
		* @param function_name function_name in table
		*/
		mem_fun_binder operator->*(const char* function_name);

		/**
		* @brief value = table[key];
		* @param key key of table
		* @return reference of field value
		*/
		template<typename T, typename KEY>
		typename lua_type_traits<T>::get_type getField(const KEY& key)const
		{
			lua_State* state = state_();
			typedef typename lua_type_traits<T>::get_type get_type;
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return get_type();
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			lua_type_traits<KEY>::push(state, key);
			lua_gettable(state, stackIndex);
			return lua_type_traits<T>::get(state, -1);
		}
		/**
		* @brief value = table[key];
		* @param key key of table
		* @return reference of field value
		*/
		template<typename T>
		typename lua_type_traits<T>::get_type getField(const char* str)const
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return typename lua_type_traits<T>::get_type();
			}
			util::ScopedSavedStack save(state);
			lua_getfield(state, pushStackIndex_(state), str);
			return lua_type_traits<T>::get(state, -1);
		}

		/**
		* @brief value = table[key];
		* @param key key of table
		* @return reference of field value
		*/
		template<typename T>
		typename lua_type_traits<T>::get_type getField(const std::string& str)const
		{
			return getField(str.c_str());
		}
		template<typename KEY>
		LuaStackRef getField(const KEY& key)const;

		/**
		* @brief foreach table fields
		*/
		template < class K, class V, class Fun> void foreach_table(Fun f)const
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return;
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			lua_pushnil(state);
			while (lua_next(state, stackIndex) != 0)
			{
				lua_pushvalue(state, -2);//backup key
				lua_insert(state, -3);

				typename lua_type_traits<V>::get_type value = lua_type_traits<V>::get(state, -1);
				typename lua_type_traits<K>::get_type key = lua_type_traits<K>::get(state, -2);
				f(key, value);
				lua_pop(state, 2);//pop key and value
			}
		}


		/**
		* @brief foreach table fields
		*/
		template < class K, class V, class Fun> void foreach_table_breakable(Fun f)const
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return;
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			lua_pushnil(state);
			while (lua_next(state, stackIndex) != 0)
			{
				lua_pushvalue(state, -2);//backup key
				lua_insert(state, -3);

				typename lua_type_traits<V>::get_type value = lua_type_traits<V>::get(state, -1);
				typename lua_type_traits<K>::get_type key = lua_type_traits<K>::get(state, -2);
				bool cont = f(key, value);
				lua_pop(state, 2);//pop key and value
				if (!cont)
				{
					break;
				}
			}
		}

		/**
		* @brief If type is table or userdata, return keys.
		* @return field keys
		*/
		template<typename K>
		std::vector<K> keys()const
		{
			std::vector<K> res;
			util::ScopedSavedStack save(state_());
			int stackIndex = pushStackIndex_(state_());
#if LUA_VERSION_NUM >= 502
			int size = lua_rawlen(state_(), stackIndex);
#else
			int size = lua_objlen(state_(), stackIndex);
#endif
			res.reserve(size);
			foreach_table<K, void>(gettablekey<K>(res));
			return res;
		}
		std::vector<LuaRef> keys()const;
		/**
		* @brief If type is table or userdata, return values.
		* @return field value
		*/
		template<typename V>
		std::vector<V> values()const
		{
			std::vector<V> res;
			util::ScopedSavedStack save(state_());
			int stackIndex = pushStackIndex_(state_());
#if LUA_VERSION_NUM >= 502
			size_t size = lua_rawlen(state_(), stackIndex);
#else
			size_t size = lua_objlen(state_(), stackIndex);
#endif
			res.reserve(size);
			foreach_table<void, V>(gettablevalue<V>(res));
			return res;
		}
		std::vector<LuaRef> values()const;
		/**
		* @brief If type is table or userdata, return key value pair.
		* @return key value pair
		*/
		template<typename K, typename V>
		std::map<K, V> map()const
		{
			std::map<K, V> res;
			foreach_table<K, V>(gettablemap<K, V>(res));
			return res;
		}
		std::map<LuaRef, LuaRef> map()const;
		/**
		* @brief value = table[key];
		* @param key key of table
		* @return reference of field value
		*/
		template<typename K>
		LuaStackRef operator[](K key)const;



		/**
		* @brief value = table[key];or table[key] = value;
		* @param key key of table
		* @return reference of field value
		*/
		template<typename K>
		TableKeyReference<K> operator[](K key);
		//@}
	};

	template<typename Derived>
	class LuaTableImpl
	{
	private:
		lua_State* state_()const
		{
			return static_cast<const Derived*>(this)->state();
		}
		int pushStackIndex_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->pushStackIndex(state);
		}
	public:

		/**
		* @brief table[key] = value;
		*/
		template<typename K, typename V>
		void setField(const K& key, const V& value)
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return;
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			util::one_push(state, key);//push table key
			util::one_push(state, value);//push value
			lua_settable(state, stackIndex);//thistable[key] = value
		}

		template<typename V>
		void setField(const char* key, const V& value)
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return;
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			util::one_push(state, value);//push value
			lua_setfield(state, stackIndex, key);//thistable[key] = value
		}
		template<typename V>
		void setField(const std::string& key, const V& value)
		{
			setField(key.c_str(), value);
		}
#if KAGUYA_USE_CPP11
		/**
		* @brief table[key] = value;
		*/
		template<typename K, typename V>
		void setField(K&& key, V&& value)
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return;
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			util::one_push(state, std::forward<K>(key));//push table key
			util::one_push(state, std::forward<V>(value));//push value
			lua_settable(state, stackIndex);//thistable[key] = value
		}
		template<typename V>
		void setField(const char* key, V&& value)
		{
			lua_State* state = state_();
			if (!state)
			{
				except::typeMismatchError(state, "is nil");
				return;
			}
			util::ScopedSavedStack save(state);
			int stackIndex = pushStackIndex_(state);
			util::one_push(state, std::forward<V>(value));//push value
			lua_setfield(state, stackIndex, key);//thistable[key] = value
		}
		template<typename V>
		void setField(const std::string& key, V&& value)
		{
			setField(key.c_str(), std::forward<V>(value));
		}
#endif
	};

}

namespace kaguya
{
	class LuaRef;
	class LuaTable;
	template<typename KEY>
	class TableKeyReference;
	class mem_fun_binder;

	template<typename Derived>
	class LuaVariantImpl :public LuaTableImpl<Derived>,
		public LuaTableOrUserDataImpl<Derived>,
		public LuaFunctionImpl<Derived>, 
		public LuaThreadImpl<Derived>, 
		public LuaBasicTypeFunctions<Derived>
	{
	private:

		lua_State* state_()const
		{
			return static_cast<const Derived*>(this)->state();
		}
		int pushStackIndex_(lua_State* state)const
		{
			return static_cast<const Derived*>(this)->pushStackIndex(state);
		}
	public:
		int type() const
		{
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			return lua_type(state, pushStackIndex_(state));
		}
		const char* typeName()const
		{
			return lua_typename(state_(), type());
		}

		template<typename T>
		bool typeTest()const
		{
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			return lua_type_traits<T>::strictCheckType(state, pushStackIndex_(state));
		}
		template<typename T>
		bool weakTypeTest()const
		{
			lua_State* state = state_();
			util::ScopedSavedStack save(state);
			return lua_type_traits<T>::checkType(state, pushStackIndex_(state));
		}



#if KAGUYA_USE_CPP11
		template<class...Args> FunctionResults operator()(Args&&... args);
#else
#define KAGUYA_TEMPLATE_PARAMETER(N)
#define KAGUYA_FUNCTION_ARGS_DEF(N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)

#define KAGUYA_OP_FN_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline FunctionResults operator()(KAGUYA_FUNCTION_ARGS_DEF(N));

			KAGUYA_OP_FN_DEF(0)

#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_FUNCTION_ARGS_DEF
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>
#define KAGUYA_FUNCTION_ARGS_DEF(N) KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG)

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PUSH_ARG_DEF(N) KAGUYA_PP_CAT(a,N) 

			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_OP_FN_DEF)
#undef KAGUYA_OP_FN_DEF
#undef KAGUYA_TEMPLATE_PARAMETER

#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_CALL_DEF
#undef KAGUYA_OP_FN_DEF
#endif
	};

}
namespace kaguya
{

	class LuaUserData;
	class LuaTable;
	class LuaFunction;
	class LuaThread;
	template<typename KEY>
	class TableKeyReference;
	class FunctionResults;
	class mem_fun_binder;

	class LuaRef;
	class LuaStackRef;

	template<>
	struct lua_type_traits<LuaRef>
	{
		typedef LuaRef get_type;
		typedef const LuaRef& push_type;

		static bool checkType(lua_State* l, int index)
		{
			return true;
		}
		static bool strictCheckType(lua_State* l, int index)
		{
			return false;
		}

		static get_type get(lua_State* l, int index);
		static int push(lua_State* l, push_type v);
	};
	template<>	struct lua_type_traits<const LuaRef&> :lua_type_traits<LuaRef> {};

	template<>
	struct lua_type_traits<LuaStackRef>
	{
		typedef LuaStackRef get_type;
		typedef const LuaStackRef& push_type;

		static bool checkType(lua_State* l, int index)
		{
			return true;
		}
		static bool strictCheckType(lua_State* l, int index)
		{
			return false;
		}

		static get_type get(lua_State* l, int index);
		static int push(lua_State* l, push_type v);
	};
	template<>	struct lua_type_traits<const LuaStackRef&> :lua_type_traits<LuaStackRef> {};

	namespace util
	{
		template<class Result>
		inline Result get_result_impl(lua_State* l, int startindex, types::typetag<Result>)
		{
			return lua_type_traits<Result>::get(l, startindex);
		}
#if KAGUYA_USE_CPP11
		inline standard::tuple<> get_result_tuple_impl(lua_State* l, int index, types::typetag<standard::tuple<> > tag)
		{
			return standard::tuple<>();
		}
		template<typename T, typename... TYPES>
		inline standard::tuple<T, TYPES...> get_result_tuple_impl(lua_State* l, int index, types::typetag<standard::tuple<T, TYPES...> > tag)
		{
			return standard::tuple_cat(standard::tuple<T>(lua_type_traits<T>::get(l, index)),
				get_result_tuple_impl(l, index + 1, types::typetag<standard::tuple<TYPES...> >()));
		}
		template<typename... TYPES>
		inline standard::tuple<TYPES...> get_result_impl(lua_State* l, int startindex, types::typetag<standard::tuple<TYPES...> > tag)
		{
			return get_result_tuple_impl<TYPES...>(l, startindex, tag);
		}
#else

		inline standard::tuple<> get_result_impl(lua_State *l, int startindex, types::typetag<standard::tuple<> > tag)
		{
			return standard::tuple<>();
		}

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_TARG(N) KAGUYA_PP_CAT(A,N)
#define KAGUYA_GET_DEF(N) lua_type_traits<KAGUYA_PP_CAT(A,N)>::get(l, N + startindex - 1) 
#define KAGUYA_GET_TUPLE_DEF(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>\
		inline standard::tuple<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TARG)> get_result_impl(lua_State *l,int startindex,types::typetag<standard::tuple<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TARG)> >)\
		{\
			return standard::tuple<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TARG) >(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_GET_DEF));\
		}
		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_GET_TUPLE_DEF)
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_TARG
#undef KAGUYA_GET_DEF
#undef KAGUYA_GET_TUPLE_DEF
#endif

			template<class Result>
		inline Result get_result(lua_State* l, int startindex)
		{
			return get_result_impl(l, startindex, types::typetag<Result>());
		}
		template<>
		inline void get_result<void>(lua_State* l, int startindex)
		{
		}
	}


	/**
	* Reference of Lua any type value.
	*/
	class LuaRef :public Ref::RegistoryRef, public LuaVariantImpl<LuaRef>
	{
		friend class LuaUserData;
		friend class LuaTable;
		friend class LuaFunction;
		friend class LuaThread;
	private:
		typedef void (LuaRef::*bool_type)() const;
		void this_type_does_not_support_comparisons() const {}


		static lua_State* toMainThread(lua_State* state)
		{
#if LUA_VERSION_NUM >= 502
			if (state)
			{
				lua_rawgeti(state, LUA_REGISTRYINDEX, LUA_RIDX_MAINTHREAD);
				lua_State* mainthread = lua_tothread(state, -1);
				lua_pop(state, 1);
				if (mainthread)
				{
					return mainthread;
				}
			}
#endif
			return state;
		}
	public:


		LuaRef(const Ref::RegistoryRef& src) :Ref::RegistoryRef(src)
		{
		}
		LuaRef(const LuaRef& src) :Ref::RegistoryRef(src)
		{
		}
		LuaRef& operator =(const LuaRef& src)
		{
			static_cast<RegistoryRef&>(*this) = src;
			return *this;
		}

#if KAGUYA_USE_CPP11

		LuaRef(LuaRef&& src)throw() :Ref::RegistoryRef(std::move(src)) {}

		LuaRef& operator =(LuaRef&& src)throw()
		{
			swap(src);
			return *this;
		}

		LuaRef(RegistoryRef&& src)throw() :Ref::RegistoryRef(std::move(src)) {}
		template<typename T>
		LuaRef(lua_State* state, T&& v, Ref::NoMainCheck) : Ref::RegistoryRef(state, std::move(v), Ref::NoMainCheck()) {}
		template<typename T>
		LuaRef(lua_State* state, T&& v) : Ref::RegistoryRef(state, std::move(v)) {}
#endif

		LuaRef() {}
		LuaRef(lua_State* state) :Ref::RegistoryRef(state) {}

		LuaRef(lua_State* state, StackTop, Ref::NoMainCheck) :Ref::RegistoryRef(state, StackTop(), Ref::NoMainCheck()) {}

		LuaRef(lua_State* state, StackTop) :Ref::RegistoryRef(state, StackTop())
		{
		}


		template<typename T>
		LuaRef(lua_State* state, const T& v, Ref::NoMainCheck) : Ref::RegistoryRef(state, v, Ref::NoMainCheck()) {}
		template<typename T>
		LuaRef(lua_State* state, const T& v) : Ref::RegistoryRef(state, v) {}

		bool isNilref()const { return state() == 0 || ref_ == LUA_REFNIL; }



		//push to Lua stack
		int push()const
		{
			return push(state());
		}
		int push(lua_State* s)const
		{
			if (isNilref())
			{
				lua_pushnil(s);
				return 1;
			}
#if LUA_VERSION_NUM >= 502
			if (s != state())
			{//state check
				assert(toMainThread(s) == toMainThread(state()));
			}
#endif
			lua_rawgeti(s, LUA_REGISTRYINDEX, ref_);
			return 1;
		}
		int pushStackIndex(lua_State* state)const
		{
			push(state);
			return lua_gettop(state);
		}



		const void* native_pointer()const
		{
			util::ScopedSavedStack save(state());
			push(state());
			return lua_topointer(state(), -1);
		}
		//@}
		static void putindent(std::ostream& os, int indent)
		{
			while (indent-- > 0)
			{
				os << "  ";
			}
		}
	};




	inline lua_type_traits<LuaRef>::get_type lua_type_traits<LuaRef>::get(lua_State* l, int index)
	{
		lua_pushvalue(l, index);
		return LuaRef(l, StackTop());
	}
	inline int lua_type_traits<LuaRef>::push(lua_State* l, lua_type_traits<LuaRef>::push_type v)
	{
		v.push(l);
		return 1;
	}



	class LuaStackRef :public Ref::StackRef, public LuaVariantImpl<LuaStackRef>
	{
	public:
		LuaStackRef() :Ref::StackRef(0,0,false)
		{
		}
		LuaStackRef(lua_State* s, int index) :Ref::StackRef(s, index, false)
		{
		}
		LuaStackRef(lua_State* s, int index,bool popAtDestruct) :Ref::StackRef(s, index, popAtDestruct)
		{
		}
#if KAGUYA_USE_CPP11
		LuaStackRef(LuaStackRef&& src) : Ref::StackRef(std::move(src))
		{
			src.pop_ = false;
		}
#else
		LuaStackRef(const LuaStackRef&src) : Ref::StackRef(src)
		{
			src.pop_ = false;
		}
#endif
	};
	inline lua_type_traits<LuaStackRef>::get_type lua_type_traits<LuaStackRef>::get(lua_State* l, int index)
	{
		return LuaStackRef(l, index);
	}
	inline int lua_type_traits<LuaStackRef>::push(lua_State* l, lua_type_traits<LuaStackRef>::push_type v)
	{
		v.push(l);
		return 1;
	}



	//! Reference to Lua userdata
	class  LuaUserData :public Ref::RegistoryRef
		, public LuaTableOrUserDataImpl<LuaUserData>
		, public LuaBasicTypeFunctions<LuaUserData>
	{

		void typecheck()
		{
			if (type() != TYPE_USERDATA)
			{
				except::typeMismatchError(state(), "not user data");
				Ref::RegistoryRef::unref();
			}
		}
	public:
		operator LuaRef() {
			push(state());
			return LuaRef(state(), StackTop());
		}
		LuaUserData(lua_State* state, StackTop) :Ref::RegistoryRef(state, StackTop())
		{
			typecheck();
		}
		LuaUserData(lua_State* state, const NewTable& table) :Ref::RegistoryRef(state, table)
		{
			typecheck();
		}
		LuaUserData(lua_State* state) :Ref::RegistoryRef(state, NewTable())
		{
			typecheck();
		}
		LuaUserData()
		{
			typecheck();
		}
	};


	template<>	struct lua_type_traits<LuaUserData> {
		typedef LuaUserData get_type;
		typedef LuaUserData push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TUSERDATA;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TUSERDATA || lua_isnil(l, index);
		}
		static LuaUserData get(lua_State* l, int index)
		{
			lua_pushvalue(l, index);
			return LuaUserData(l, StackTop());
		}
		static int push(lua_State* l, const LuaUserData& ref)
		{
			ref.push(l);
			return 1;
		}
	};
	template<>	struct lua_type_traits<const LuaUserData&> :lua_type_traits<LuaUserData> {};


	class LuaTable :public Ref::RegistoryRef
		, public LuaTableImpl<LuaTable>
		, public LuaTableOrUserDataImpl<LuaTable>
		, public LuaBasicTypeFunctions<LuaTable>
	{

		void typecheck()
		{
			if (type() != TYPE_TABLE)
			{
				except::typeMismatchError(state(), "not table");
				Ref::RegistoryRef::unref();
			}
		}
	public:
		operator LuaRef() {
			push(state());
			return LuaRef(state(), StackTop());
		}
		LuaTable(lua_State* state, StackTop) :Ref::RegistoryRef(state, StackTop())
		{
			typecheck();
		}
		LuaTable(lua_State* state, const NewTable& table) :Ref::RegistoryRef(state, table)
		{
			typecheck();
		}
		LuaTable(lua_State* state) :Ref::RegistoryRef(state, NewTable())
		{
			typecheck();
		}
		LuaTable()
		{
			typecheck();
		}
	};

	template<>	struct lua_type_traits<LuaTable> {
		typedef LuaTable get_type;
		typedef LuaTable push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_istable(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_istable(l, index) || lua_isnil(l, index);
		}
		static LuaTable get(lua_State* l, int index)
		{
			lua_pushvalue(l, index);
			return LuaTable(l, StackTop());
		}
		static int push(lua_State* l, const LuaTable& ref)
		{
			ref.push(l);
			return 1;
		}
	};
	template<>	struct lua_type_traits<const LuaTable&> :lua_type_traits<LuaTable> {};

	/**
	* Reference of Lua function.
	*/
	class LuaFunction :public Ref::RegistoryRef
		, public LuaFunctionImpl<LuaFunction>
		, public LuaBasicTypeFunctions<LuaFunction>
	{
		void typecheck()
		{
			if (type() != TYPE_FUNCTION)
			{
				except::typeMismatchError(state(), "not function");
				RegistoryRef::unref();
			}
		}

	public:

		LuaFunction(lua_State* state, StackTop) :Ref::RegistoryRef(state, StackTop())
		{
			typecheck();
		}
		LuaFunction()
		{
		}

		/**
		* @name loadstring
		* @brief load lua code .
		* @param state pointer to lua_State
		* @param luacode string
		*/
		static LuaFunction loadstring(lua_State* state, const std::string& luacode)
		{
			return loadstring(state, luacode.c_str());
		}
		/**
		* @name loadstring
		* @brief load lua code .
		* @param state pointer to lua_State
		* @param luacode string
		*/
		static LuaFunction loadstring(lua_State* state, const char* luacode)
		{
			util::ScopedSavedStack save(state);
			int status = luaL_loadstring(state, luacode);

			if (status)
			{
				ErrorHandler::handle(status, state);
				return LuaRef(state);
			}
			return LuaFunction(state, StackTop());
		}


		/**
		* @name loadfile
		* @brief If there are no errors,compiled file as a Lua function and return.
		*  Otherwise send error message to error handler and return nil reference
		* @param file  file path of lua script
		* @return reference of lua function
		*/
		static LuaFunction loadfile(lua_State* state, const std::string& file)
		{
			return loadfile(state, file.c_str());
		}
		static LuaFunction loadfile(lua_State* state, const char* file)
		{
			util::ScopedSavedStack save(state);

			int status = luaL_loadfile(state, file);

			if (status)
			{
				ErrorHandler::handle(status, state);
				return LuaRef(state);
			}
			return LuaFunction(state, StackTop());
		}


		struct LuaLoadStreamWrapper
		{
			LuaLoadStreamWrapper(std::istream& stream) :preload_(false), stream_(stream)
			{
				buffer_.reserve(512);
				skipComment();
			}
			std::vector<char> skipBom()
			{
				std::vector<char> preload;
				preload.reserve(512);
				const char* bomseq = "\xEF\xBB\xBF";
				while (stream_.good())
				{
					int c = stream_.get();
					preload.push_back(c);
					if (c != *bomseq)
					{
						break;
					}
					bomseq++;
					if ('\0' == *bomseq)
					{
						preload.clear();
					}
				}
				return preload;
			}
			void skipComment()
			{
				std::vector<char> preload = skipBom();
				if (!preload.empty() && preload.front() == '#')
				{
					std::vector<char>::iterator lf = std::find(preload.begin(), preload.end(), '\n');
					if (lf != preload.end())
					{
						buffer_.assign(lf, preload.end());
					}
					else
					{
						std::string comment;
						std::getline(stream_, comment);
					}
				}
				else
				{
					buffer_.assign(preload.begin(), preload.end());
				}
				preload_ = !buffer_.empty();
			}
			

			static const char *getdata(lua_State *, void *ud, size_t *size) {
				LuaLoadStreamWrapper *loader = static_cast<LuaLoadStreamWrapper *>(ud);

				if (loader->preload_)
				{
					loader->preload_ = false;
				}
				else
				{
					loader->buffer_.clear();
				}

				while (loader->stream_.good() && loader->buffer_.size() < loader->buffer_.capacity())
				{
					int c = loader->stream_.get();
					if (!loader->stream_.eof())
					{
						loader->buffer_.push_back(c);
					}
				}
				*size = loader->buffer_.size();
				return loader->buffer_.empty()?0:&loader->buffer_[0];
			}
		private:
			bool preload_;
			std::vector<char> buffer_;
			std::istream& stream_;
		};

		/**
		* @name loadstream
		* @brief If there are no errors,compiled stream as a Lua function and return.
		*  Otherwise send error message to error handler and return nil reference
		* @param file  file path of lua script
		* @return reference of lua function
		*/
		static LuaFunction loadstream(lua_State* state, std::istream& stream, const std::string& chunkname = "")
		{
			return loadstream(state, stream, chunkname.c_str());
		}
		static LuaFunction loadstream(lua_State* state, std::istream& stream, const char* chunkname = "")
		{
			LuaLoadStreamWrapper wrapper(stream);
#if LUA_VERSION_NUM >= 502
			int status = lua_load(state, &LuaLoadStreamWrapper::getdata, &wrapper, chunkname, 0);
#else
			int status = lua_load(state, &LuaLoadStreamWrapper::getdata, &wrapper, chunkname);
#endif
			if (status)
			{
				ErrorHandler::handle(status, state);
				return LuaRef(state);
			}
			return LuaFunction(state, StackTop());
		}
	};

	/**
	* Reference of Lua thread(==coroutine).
	*/
	class LuaThread :public Ref::RegistoryRef
		, public LuaThreadImpl<LuaThread>
		, public LuaBasicTypeFunctions<LuaThread>
	{
		void typecheck()
		{
			if (type() != TYPE_THREAD)
			{
				except::typeMismatchError(state(), "not lua thread");
				RegistoryRef::unref();
			}
		}
	public:
		LuaThread(lua_State* state, StackTop) :Ref::RegistoryRef(state, StackTop())
		{
			typecheck();
		}
		LuaThread(lua_State* state, const NewThread& t) :Ref::RegistoryRef(state, t)
		{
		}
		LuaThread(lua_State* state) :Ref::RegistoryRef(state, NewThread())
		{
		}
		LuaThread()
		{
		}
		void setFunction(const LuaFunction& f)
		{
			typecheck();
			lua_State* corstate = get<lua_State*>();
			lua_settop(corstate,0);
			f.push(corstate);
		}
	};


}



#if KAGUYA_USE_CPP11
#else
namespace std
{
	template <> inline void swap(kaguya::LuaRef& a, kaguya::LuaRef& b)
	{
		a.swap(b);
	}
}
#endif
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>
#include <vector>


#if KAGUYA_USE_CPP11
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)

#include <string>



namespace kaguya
{
	namespace nativefunction
	{
		namespace cpp11impl
		{
			template<class ThisType, class Res, class... FArgs, class... Args>
			Res invoke(Res(ThisType::*f)(FArgs...), ThisType* this_, Args&&... args)
			{
				if (!this_)
				{
					throw LuaTypeMismatch("type mismatch!!");
				}
				return (this_->*f)(std::forward<Args>(args)...);
			}

			template<class ThisType, class... FArgs, class... Args>
			void invoke(void (ThisType::*f)(FArgs...), ThisType* this_, Args&&... args)
			{
				if (!this_)
				{
					throw LuaTypeMismatch("type mismatch!!");
				}
				(this_->*f)(std::forward<Args>(args)...);
			}

			template<class ThisType, class Res, class... FArgs, class... Args>
			Res invoke(Res(ThisType::*f)(FArgs...)const, const ThisType* this_, Args&&... args)
			{
				if (!this_)
				{
					throw LuaTypeMismatch("type mismatch!!");
				}
				return (this_->*f)(std::forward<Args>(args)...);
			}

			template<class ThisType, class... FArgs, class... Args>
			void invoke(void (ThisType::*f)(FArgs...)const, const ThisType* this_, Args&&... args)
			{
				(this_->*f)(std::forward<Args>(args)...);
			}
			template<class F, class... Args>
			typename standard::result_of<F(Args...)>::type
				invoke(const F& f, Args&&... args)
			{
				return f(std::forward<Args>(args)...);
			}

			template<std::size_t... indexes>
			struct index_tuple {};

			template<std::size_t first, std::size_t last, class result = index_tuple<>, bool flag = first >= last>
			struct index_range
			{
				using type = result;
			};

			template<std::size_t step, std::size_t last, std::size_t... indexes>
			struct index_range<step, last, index_tuple<indexes...>, false>
				: index_range<step + 1, last, index_tuple<indexes..., step>>
			{};

			template<class Ret, class... Args>
			struct invoke_signature_type {};

			template<class F>
			struct arg_count;
			template<class R, class... Args>
			struct arg_count<invoke_signature_type<R, Args...> > : traits::integral_constant<size_t, sizeof...(Args)>
			{};

			template<class F>
			struct arg_index_range;

			template<class R, class... Args>
			struct arg_index_range<invoke_signature_type<R, Args...> > : index_range<1, sizeof...(Args)+1>
			{};


			template <typename T>
			struct functor_f_signature {};

			template <typename T, typename Ret, typename... Args>
			struct functor_f_signature<Ret(T::*)(Args...) const> {
				typedef invoke_signature_type<Ret, Args...> type;
			};

#if _MSC_VER
			template <typename T>
			struct f_signature : public functor_f_signature<decltype(&T::operator())> {};
#else

			template <typename T, typename Enable=void>
			struct f_signature {};

			template < typename T, typename = void>
			struct has_operator_fn :std::false_type {};
			template < typename T >
			struct has_operator_fn<T, typename std::enable_if<!std::is_same<void, decltype(&T::operator())>::value>::type> :std::true_type {};

			template <typename T>
			struct f_signature<T,typename std::enable_if<has_operator_fn<T>::value>::type>
				: public functor_f_signature<decltype(&T::operator())> {};
#endif


			template <typename T, typename Ret, typename... Args>
			struct f_signature<Ret(T::*)(Args...)> {
				typedef invoke_signature_type<Ret, T*, Args...> type;
			};
			template <typename T, typename Ret, typename... Args>
			struct f_signature<Ret(T::*)(Args...) const> {
				typedef invoke_signature_type<Ret, const T*, Args...> type;
			};
			template<class Ret, class... Args>
			struct f_signature<Ret(*)(Args...)> {
				typedef invoke_signature_type<Ret, Args...> type;
			};

			template<class F, class Ret, class... Args, size_t... Indexes>
			int _call_apply(lua_State* state, const F& f, index_tuple<Indexes...>, invoke_signature_type<Ret, Args...>)
			{
				return lua_type_traits<Ret>::push(state, invoke(f, lua_type_traits<Args>::get(state, Indexes)...));
			}
			template<class F, class... Args, size_t... Indexes>
			int _call_apply(lua_State* state, const F& f, index_tuple<Indexes...>, invoke_signature_type<void, Args...>)
			{
				invoke(f, lua_type_traits<Args>::get(state, Indexes)...);
				return 0;
			}

			inline bool all_true()
			{
				return true;
			}
			template<class...Args>bool all_true(bool b, Args... args)
			{
				return b && all_true(args...);
			}

			inline std::string join(const char* delim)
			{
				return "";
			}
			template<class...Args>std::string join(const char* delim, const std::string& str, Args... args)
			{
				return str + delim + join(delim, args...);
			}


			template<class R, class... Args, size_t... Indexes>
			bool _ctype_apply(lua_State* state, index_tuple<Indexes...>, invoke_signature_type<R, Args...>)
			{
				return all_true(lua_type_traits<Args>::checkType(state, Indexes)...);
			}
			template<class R, class... Args, size_t... Indexes>
			bool _sctype_apply(lua_State* state, index_tuple<Indexes...>, invoke_signature_type<R, Args...>)
			{
				return all_true(lua_type_traits<Args>::strictCheckType(state, Indexes)...);
			}
			template<class R, class... Args>
			std::string _type_name_apply(invoke_signature_type<R, Args...>)
			{
				return join(",", typeid(Args).name()...);
			}

			///! for data member
			//@{
			template <typename T, typename MemType>
			struct f_signature<MemType T::*> {
				typedef MemType T::*type;
			};
			template<class MemType, class T>
			struct arg_count<MemType T::*> : traits::integral_constant<size_t, 2>
			{};
			template <typename T, typename MemType>
			struct arg_index_range<MemType T::*> : index_range<1, 2> {};

			template<class MemType, class T, class unusedindex>
			int _call_apply(lua_State* state, MemType T::* m, unusedindex, MemType T::*)
			{
				T* this_ = lua_type_traits<T*>::get(state, 1);
				if (lua_gettop(state) == 1)
				{
					if (!this_)
					{
						const T* this_ = lua_type_traits<const T*>::get(state, 1);
						if (!this_)
						{
							throw LuaTypeMismatch("type mismatch!!");
						}
						if (is_usertype<MemType>::value && !traits::is_pointer<MemType>::value)
						{
							return lua_type_traits<standard::reference_wrapper<const MemType> >::push(state, standard::reference_wrapper<const MemType>(this_->*m));
						}
						else
						{
							return lua_type_traits<MemType>::push(state, this_->*m);
						}
					}
					else
					{
						if (is_usertype<MemType>::value && !traits::is_pointer<MemType>::value)
						{
							return lua_type_traits<standard::reference_wrapper<MemType> >::push(state, standard::reference_wrapper<MemType>(this_->*m));
						}
						else
						{
							return lua_type_traits<MemType>::push(state, this_->*m);
						}
					}
				}
				else
				{
					if (!this_ || !lua_type_traits<MemType>::checkType(state, 2))
					{
						throw LuaTypeMismatch("type mismatch!!");
					}
					this_->*m = lua_type_traits<MemType>::get(state, 2);
					return 0;
				}
			}
			template<class MemType, class T, class unusedindex>
			bool _ctype_apply(lua_State* state, unusedindex, MemType T::*)
			{
				bool thistypecheck = lua_type_traits<T>::checkType(state, 1);
				if (thistypecheck && lua_gettop(state) == 2)
				{
					return lua_type_traits<MemType>::checkType(state, 2);
				}
				return thistypecheck;
			}
			template<class MemType, class T, class unusedindex>
			bool _sctype_apply(lua_State* state, unusedindex, MemType T::*)
			{
				bool thistypecheck = lua_type_traits<T>::strictCheckType(state, 1);
				if (thistypecheck && lua_gettop(state) == 2)
				{
					return lua_type_traits<MemType>::strictCheckType(state, 2);
				}
				return thistypecheck;
			}
			template<class MemType, class T>
			std::string _type_name_apply(MemType T::*)
			{
				return std::string(typeid(T*).name()) + ",[OPT] " + typeid(MemType).name();
			}
			//@}

			///! for constructor
			//@{
			template<class ClassType, class... Args>
			struct constructor_signature_type :invoke_signature_type<ClassType, Args...>
			{
			};
			template <class ClassType, class... Args>
			struct f_signature<constructor_signature_type<ClassType, Args...> > {
				typedef constructor_signature_type<ClassType, Args...> type;
			};
			template<class ClassType, class... Args>
			struct arg_index_range<constructor_signature_type<ClassType, Args...> > : index_range<1, sizeof...(Args)+1>
			{};
			template<class ClassType, class... Args>
			struct arg_count<constructor_signature_type<ClassType, Args...> > : traits::integral_constant<size_t, sizeof...(Args)>
			{};

			template <class ClassType, class... Args, size_t... Indexes>
			int _call_apply(lua_State* state, constructor_signature_type<ClassType, Args...>, index_tuple<Indexes...>, constructor_signature_type<ClassType, Args...>)
			{
				typedef ObjectWrapper<ClassType> wrapper_type;
				void *storage = lua_newuserdata(state, sizeof(wrapper_type));
				new(storage) wrapper_type(lua_type_traits<Args>::get(state, Indexes)...);

				class_userdata::setmetatable<ClassType>(state);
				return 1;
			}
			//@}


			template<class F>
			int call(lua_State* state, const F& f)
			{
				typedef typename f_signature<F>::type fsigtype;
				typedef typename arg_index_range<fsigtype>::type index;

				return _call_apply(state, f, index(), fsigtype());
			}
			template<class F>
			bool checkArgTypes(lua_State* state, const F& f)
			{
				typedef typename f_signature<F>::type fsigtype;
				typedef typename arg_index_range<fsigtype>::type index;
				return _ctype_apply(state, index(), fsigtype());
			}
			template<class F>
			bool strictCheckArgTypes(lua_State* state, const F& f)
			{
				typedef typename f_signature<F>::type fsigtype;
				typedef typename arg_index_range<fsigtype>::type index;
				return _sctype_apply(state, index(), fsigtype());
			}

			template<class F>
			std::string argTypesName(const F& f)
			{
				typedef typename f_signature<F>::type fsigtype;
				return _type_name_apply(fsigtype());
			}
			template<class F>
			int argCount(const F& f)
			{
				typedef typename f_signature<F>::type fsigtype;
				return arg_count<fsigtype>::value;
			}

			template< typename T, typename Enable = void>
			struct is_callable : traits::integral_constant<bool, false> {};
			template< typename T>
			struct is_callable<T, typename traits::enable_if<
				!traits::is_same<void, typename f_signature<T>::type>::value
				, void>::type > : traits::integral_constant<bool, true> {};

			template<typename ClassType, typename... Args> struct functionToConstructorSignature;
			template<typename ClassType, typename... Args> struct functionToConstructorSignature<ClassType(Args...) >
			{
				typedef constructor_signature_type<ClassType, Args...> type;
			};
			template<typename ClassType, typename... Args> struct functionToConstructorSignature<ClassType, ClassType(Args...) >//class type check version
			{
				typedef constructor_signature_type<ClassType, Args...> type;
			};
		}
		using cpp11impl::call;
		using cpp11impl::checkArgTypes;
		using cpp11impl::strictCheckArgTypes;
		using cpp11impl::argTypesName;
		using cpp11impl::argCount;
		using cpp11impl::constructor_signature_type;
		using cpp11impl::functionToConstructorSignature;
		using cpp11impl::is_callable;
	}
}
#else
// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)

#include <string>


namespace kaguya
{
	namespace nativefunction
	{
		namespace cpp03impl
		{
			
#if defined(_MSC_VER) && _MSC_VER <= 1700
			//can not detect callable at MSVC
			template< typename T>
			struct is_callable : traits::integral_constant<bool, true> {};
#else
			template< typename T>
			struct is_callable : traits::integral_constant<bool, false> {};
#endif

			inline int call(lua_State* state, void(*f)())
			{
				f();
				return 0;
			}
			template<>struct is_callable< void(*)()> : traits::integral_constant<bool, true> {};

			inline bool checkArgTypes(lua_State* state, void(*f)())
			{
				return true;
			}
			inline bool strictCheckArgTypes(lua_State* state, void(*f)())
			{
				return true;
			}
			inline std::string argTypesName(void(*f)())
			{
				return "void";
			}
			inline int argCount(void(*f)())
			{
				return 0;
			}

			inline int call(lua_State* state,standard::function<void()> f)
			{
				f();
				return 0;
			}
			template<>struct is_callable<standard::function<void()> > : traits::integral_constant<bool, true> {};
			inline bool checkArgTypes(lua_State* state, standard::function<void()> f)
			{
				return true;
			}
			inline bool strictCheckArgTypes(lua_State* state, standard::function<void()> f)
			{
				return true;
			}
			inline std::string argTypesName(standard::function<void()> f)
			{
				return "void";
			}
			inline int argCount(standard::function<void()> f)
			{
				return 0;
			}
#define KAGUYA_GET_OFFSET 
#define KAGUYA_GET_CONCAT_REP(N) ,lua_type_traits<KAGUYA_PP_CAT(A,N)>::get(state, N KAGUYA_GET_OFFSET)
#define KAGUYA_GET_REP(N) lua_type_traits<KAGUYA_PP_CAT(A,N)>::get(state, N KAGUYA_GET_OFFSET)

#define KAGUYA_STRICT_TYPECHECK_REP(N) && lua_type_traits<KAGUYA_PP_CAT(A,N)>::strictCheckType(state, N KAGUYA_GET_OFFSET)
#define KAGUYA_TYPECHECK_REP(N) && lua_type_traits<KAGUYA_PP_CAT(A,N)>::checkType(state, N KAGUYA_GET_OFFSET)
#define KAGUYA_TYPENAME_REP(N) + typeid(KAGUYA_PP_CAT(A,N)).name() + ","

#define KAGUYA_GET_REPEAT_CONCAT(N) KAGUYA_PP_REPEAT(N,KAGUYA_GET_CONCAT_REP)
#define KAGUYA_GET_REPEAT(N) KAGUYA_PP_REPEAT_ARG(N,KAGUYA_GET_REP)

#define KAGUYA_FUNC_DEF(N) void (*f)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))
#define KAGUYA_FUNC_TYPE(N) void (*)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))
#define KAGUYA_CALL_FN_DEF(N) \
			template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>\
			inline int call(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				f(KAGUYA_GET_REPEAT(N));\
				return 0;\
			}\
			template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>struct is_callable<KAGUYA_FUNC_TYPE(N)> : traits::integral_constant<bool, true> {};

			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)
#undef KAGUYA_CALL_FN_DEF
#undef KAGUYA_FUNC_DEF
#undef KAGUYA_FUNC_TYPE
#define KAGUYA_FUNC_DEF(N) Ret (*f)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))
#define KAGUYA_FUNC_TYPE(N) Ret (*)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))
#define KAGUYA_CALL_FN_DEF(N) \
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			inline int call(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return lua_type_traits<Ret>::push(state, f(KAGUYA_GET_REPEAT(N)));\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>struct is_callable<KAGUYA_FUNC_TYPE(N)> : traits::integral_constant<bool, true> {};\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool checkArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return true KAGUYA_PP_REPEAT(N,KAGUYA_TYPECHECK_REP);\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool strictCheckArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return true KAGUYA_PP_REPEAT(N,KAGUYA_STRICT_TYPECHECK_REP);\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			std::string argTypesName(KAGUYA_FUNC_DEF(N))\
			{\
				return std::string() KAGUYA_PP_REPEAT(N,KAGUYA_TYPENAME_REP);\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			int argCount(KAGUYA_FUNC_DEF(N))\
			{\
				return N  KAGUYA_GET_OFFSET;\
			}

				KAGUYA_CALL_FN_DEF(0)
				KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)

#undef KAGUYA_GET_OFFSET
#define KAGUYA_GET_OFFSET + 1
#undef KAGUYA_CALL_FN_DEF
#define KAGUYA_MEM_ATTRBUTE 
#undef KAGUYA_FUNC_DEF
#undef KAGUYA_FUNC_TYPE				
#define KAGUYA_FUNC_DEF(N) void (ThisType::*f)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))KAGUYA_MEM_ATTRBUTE
#define KAGUYA_FUNC_TYPE(N) void (ThisType::*)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))KAGUYA_MEM_ATTRBUTE
#define KAGUYA_CALL_FN_DEF(N) \
			template<typename ThisType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			inline int call(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				KAGUYA_MEM_ATTRBUTE ThisType* this_ = lua_type_traits<KAGUYA_MEM_ATTRBUTE ThisType*>::get(state, 1); \
				(this_->*f)(KAGUYA_GET_REPEAT(N));\
				return 0;\
			}\
			template<typename ThisType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>struct is_callable<KAGUYA_FUNC_TYPE(N)> : traits::integral_constant<bool, true> {};

				KAGUYA_CALL_FN_DEF(0)
				KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)

#undef KAGUYA_MEM_ATTRBUTE
#define KAGUYA_MEM_ATTRBUTE const  
				KAGUYA_CALL_FN_DEF(0)
				KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)
#undef KAGUYA_MEM_ATTRBUTE


#undef KAGUYA_CALL_FN_DEF
#define KAGUYA_MEM_ATTRBUTE 
#undef KAGUYA_FUNC_DEF
#undef KAGUYA_FUNC_TYPE
#define KAGUYA_FUNC_DEF(N) Ret (ThisType::*f)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))KAGUYA_MEM_ATTRBUTE
#define KAGUYA_FUNC_TYPE(N) Ret (ThisType::*)(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))KAGUYA_MEM_ATTRBUTE
#define KAGUYA_CALL_FN_DEF(N) \
			template<typename ThisType,typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			inline int call(lua_State* state,KAGUYA_FUNC_DEF(N))\
			{\
				KAGUYA_MEM_ATTRBUTE ThisType* this_ = lua_type_traits<KAGUYA_MEM_ATTRBUTE ThisType*>::get(state, 1); \
				return lua_type_traits<Ret>::push(state, (this_->*f)(KAGUYA_GET_REPEAT(N)));\
			}\
			template<typename ThisType,typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>struct is_callable<KAGUYA_FUNC_TYPE(N)> : traits::integral_constant<bool, true> {};\
			template<typename ThisType,typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool checkArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return lua_type_traits<KAGUYA_MEM_ATTRBUTE ThisType*>::checkType(state, 1) \
					KAGUYA_PP_REPEAT(N,KAGUYA_TYPECHECK_REP);\
			}\
			template<typename ThisType,typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool strictCheckArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return lua_type_traits<KAGUYA_MEM_ATTRBUTE ThisType*>::strictCheckType(state, 1) \
					KAGUYA_PP_REPEAT(N,KAGUYA_STRICT_TYPECHECK_REP);\
			}\
			template<typename ThisType,typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			std::string argTypesName(KAGUYA_FUNC_DEF(N))\
			{\
				return typeid(ThisType).name() + std::string(",") KAGUYA_PP_REPEAT(N,KAGUYA_TYPENAME_REP);\
			}\
			template<typename ThisType,typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			int argCount(KAGUYA_FUNC_DEF(N))\
			{\
				return N  KAGUYA_GET_OFFSET;\
			}
				KAGUYA_CALL_FN_DEF(0)
				KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)

#undef KAGUYA_MEM_ATTRBUTE
#define KAGUYA_MEM_ATTRBUTE const  
				KAGUYA_CALL_FN_DEF(0)
				KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)
#undef KAGUYA_MEM_ATTRBUTE

#undef KAGUYA_GET_OFFSET
#define KAGUYA_GET_OFFSET
#undef KAGUYA_CALL_FN_DEF
#undef KAGUYA_FUNC_DEF
#undef KAGUYA_FUNC_TYPE
#define KAGUYA_FUNC_DEF(N) const standard::function<void (KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))>& f
#define KAGUYA_FUNC_TYPE(N) standard::function<void (KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))>
#define KAGUYA_CALL_FN_DEF(N) \
			template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>\
			inline int call(lua_State* state,KAGUYA_FUNC_DEF(N))\
			{\
				f(KAGUYA_GET_REPEAT(N));\
				return 0;\
			}\
			template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>struct is_callable<KAGUYA_FUNC_TYPE(N) > : traits::integral_constant<bool, true> {};

			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)
#undef KAGUYA_CALL_FN_DEF
#undef KAGUYA_FUNC_DEF
#undef KAGUYA_FUNC_TYPE
#define KAGUYA_FUNC_DEF(N) const standard::function<Ret (KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))>& f
#define KAGUYA_FUNC_TYPE(N) standard::function<Ret (KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))>
#define KAGUYA_CALL_FN_DEF(N) \
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			inline int call(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return lua_type_traits<Ret>::push(state,f(KAGUYA_GET_REPEAT(N)));\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>struct is_callable<KAGUYA_FUNC_TYPE(N)> : traits::integral_constant<bool, true> {};\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool checkArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return true KAGUYA_PP_REPEAT(N,KAGUYA_TYPECHECK_REP);\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool strictCheckArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return true KAGUYA_PP_REPEAT(N,KAGUYA_STRICT_TYPECHECK_REP);\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			std::string argTypesName(KAGUYA_FUNC_DEF(N))\
			{\
				return std::string() KAGUYA_PP_REPEAT(N,KAGUYA_TYPENAME_REP);\
			}\
			template<typename Ret KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			int argCount(KAGUYA_FUNC_DEF(N))\
			{\
				return N  KAGUYA_GET_OFFSET;\
			}
			KAGUYA_CALL_FN_DEF(0)
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)

			///! for data member
			//@{
			template<class MemType, class T>
			int call(lua_State* state, MemType T::* m)
			{
				T* this_ = lua_type_traits<T*>::get(state, 1);
				if (lua_gettop(state) == 1)
				{
					if (!this_)
					{
						const T* this_ = lua_type_traits<const T*>::get(state, 1);
						if (!this_)
						{
							throw LuaTypeMismatch("type mismatch!!");
						}
						if (is_usertype<MemType>::value && !traits::is_pointer<MemType>::value)
						{
							return lua_type_traits<standard::reference_wrapper<const MemType> >::push(state, standard::reference_wrapper<const MemType>(this_->*m));
						}
						else
						{
							return lua_type_traits<MemType>::push(state, this_->*m);
						}
					}
					else
					{
						if (is_usertype<MemType>::value && !traits::is_pointer<MemType>::value)
						{
							return lua_type_traits<standard::reference_wrapper<MemType> >::push(state, standard::reference_wrapper<MemType>(this_->*m));
						}
						else
						{
							return lua_type_traits<MemType>::push(state, this_->*m);
						}
					}
				}
				else
				{
					if (!this_ || !lua_type_traits<MemType>::checkType(state, 2))
					{
						throw LuaTypeMismatch("type mismatch!!");
					}
					this_->*m = lua_type_traits<MemType>::get(state, 2);
					return 0;
				}
			}
#if defined(_MSC_VER) && _MSC_VER <= 1700
			//can not detect callable at MSVC
#else
			template<class MemType, class T>struct is_callable<MemType T::*> : traits::integral_constant<bool, true> {}; 
#endif

			template<class MemType, class T>
			bool checkArgTypes(lua_State* state, MemType T::* m)
			{
				bool thistypecheck = lua_type_traits<T>::checkType(state, 1);
				if (thistypecheck && lua_gettop(state) == 2)
				{
					return lua_type_traits<MemType>::checkType(state, 2);
				}
				return thistypecheck;
			}
			template<class MemType, class T>
			bool strictCheckArgTypes(lua_State* state, MemType T::* m)
			{
				bool thistypecheck = lua_type_traits<T>::strictCheckType(state, 1);
				if (thistypecheck && lua_gettop(state) == 2)
				{
					return lua_type_traits<MemType>::strictCheckType(state, 2);
				}
				return thistypecheck;
			}
			template<class MemType, class T>
			std::string argTypesName(MemType T::*)
			{
				return std::string(typeid(T*).name()) + ",[OPT] " + typeid(MemType).name();
			}
			template<class MemType, class T>
			int argCount(MemType T::*)
			{
				return 2;
			}
			//@}

			///! for constructor
			//@{
			struct null_type {};

			template<class ClassType, class A1 = null_type, class A2 = null_type
				, class A3 = null_type, class A4 = null_type, class A5 = null_type
				, class A6 = null_type, class A7 = null_type, class A8 = null_type,
			class A9 = null_type>struct constructor_signature_type {
			};

#undef KAGUYA_CALL_FN_DEF
#undef KAGUYA_FUNC_DEF
#undef KAGUYA_FUNC_TYPE
#define KAGUYA_FUNC_DEF(N)  constructor_signature_type<ClassType KAGUYA_PP_TEMPLATE_ARG_REPEAT_CONCAT(N)>
#define KAGUYA_CALL_FN_DEF(N) \
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			inline int call(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				typedef ObjectWrapper<ClassType> wrapper_type;\
				void *storage = lua_newuserdata(state, sizeof(wrapper_type));\
				new(storage) wrapper_type(KAGUYA_GET_REPEAT(N));\
				class_userdata::setmetatable<ClassType>(state);\
				return 1;\
			}\
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>struct is_callable<KAGUYA_FUNC_DEF(N)> : traits::integral_constant<bool, true> {}; \
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool checkArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return true KAGUYA_PP_REPEAT(N,KAGUYA_TYPECHECK_REP);\
			}\
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			bool strictCheckArgTypes(lua_State* state, KAGUYA_FUNC_DEF(N))\
			{\
				return true KAGUYA_PP_REPEAT(N,KAGUYA_STRICT_TYPECHECK_REP);\
			}\
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			std::string argTypesName(KAGUYA_FUNC_DEF(N))\
			{\
				return std::string() KAGUYA_PP_REPEAT(N,KAGUYA_TYPENAME_REP);\
			}\
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			int argCount(KAGUYA_FUNC_DEF(N))\
			{\
				return N KAGUYA_GET_OFFSET;\
			}
			KAGUYA_CALL_FN_DEF(0)
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_FN_DEF)


				//@}
			template<class ClassType, class FunType=void> struct functionToConstructorSignature;
			
#define KAGUYA_F_TO_CONSIG_TYPE_DEF(N)  constructor_signature_type<ClassType KAGUYA_PP_TEMPLATE_ARG_REPEAT_CONCAT(N)>
#define KAGUYA_F_TO_CONSIG_DEF(N) \
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			struct functionToConstructorSignature<ClassType(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))>\
			{\
				typedef KAGUYA_F_TO_CONSIG_TYPE_DEF(N) type;\
			};\
			template<typename ClassType KAGUYA_PP_TEMPLATE_DEF_REPEAT_CONCAT(N)>\
			struct functionToConstructorSignature<ClassType,ClassType(KAGUYA_PP_TEMPLATE_ARG_REPEAT(N))>\
			{\
				typedef KAGUYA_F_TO_CONSIG_TYPE_DEF(N) type;\
			};

			KAGUYA_F_TO_CONSIG_DEF(0)
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_F_TO_CONSIG_DEF)
		}
		using cpp03impl::call;
		using cpp03impl::checkArgTypes;
		using cpp03impl::strictCheckArgTypes;
		using cpp03impl::argTypesName;
		using cpp03impl::argCount;
		using cpp03impl::constructor_signature_type;
		using cpp03impl::functionToConstructorSignature;
		using cpp03impl::is_callable;
	}
}
#endif
namespace kaguya
{
	class VariadicArgType
	{
	public:
		VariadicArgType(lua_State* state, int startIndex) :state_(state), startIndex_(startIndex), endIndex_(lua_gettop(state) + 1)
		{
			if (startIndex_ > endIndex_)
			{
				endIndex_ = startIndex_;
			}
		}

		template<typename T>
		operator std::vector<T>()const
		{
			if (startIndex_ >= endIndex_)
			{
				return std::vector<T>();
			}
			std::vector<T> result;
			result.reserve(endIndex_ - startIndex_);
			for (int index = startIndex_; index < endIndex_; ++index)
			{
				result.push_back(lua_type_traits<T>::get(state_, index));
			}
			return result;
		}

		struct reference :public Ref::StackRef, public LuaVariantImpl<reference>
		{
			reference(lua_State* s, int index) :Ref::StackRef(s, index, false)
			{
			}

			const reference* operator->()const
			{
				return this;
			}
		};
		struct iterator
		{
			iterator(lua_State* state, int index) :state_(state), stack_index_(index)
			{
			}
			reference operator*()const
			{
				return reference(state_, stack_index_);
			}
			reference operator->()const
			{
				return reference(state_, stack_index_);
			}
			const iterator& operator++()
			{
				stack_index_++;
				return *this;
			}
			iterator operator++(int)
			{
				return iterator(state_, stack_index_++);
			}

			iterator operator+=(int n)
			{
				stack_index_ += n;
				return iterator(state_, stack_index_);
			}
			bool operator==(const iterator& other)const
			{
				return state_ == other.state_ && stack_index_ == other.stack_index_;
			}
			bool operator!=(const iterator& other)const
			{
				return !(*this == other);
			}
		private:
			lua_State* state_;
			int stack_index_;
		};
		typedef iterator const_iterator;
		typedef reference const_reference;

		iterator begin()
		{
			return iterator(state_, startIndex_);
		}
		iterator end()
		{
			return iterator(state_, endIndex_);
		}
		const_iterator cbegin()
		{
			return const_iterator(state_, startIndex_);
		}
		const_iterator cend()
		{
			return const_iterator(state_, endIndex_);
		}


		template<typename T>
		typename lua_type_traits<T>::get_type at(size_t index)const
		{
			if (index >= size())
			{
				throw std::out_of_range("variadic arguments out of range");
			}
			return lua_type_traits<T>::get(state_, startIndex_ + static_cast<int>(index));
		}

		reference at(size_t index)const
		{
			if (index >= size())
			{
				throw std::out_of_range("variadic arguments out of range");
			}
			return reference(state_, startIndex_ + static_cast<int>(index));
		}

		reference operator[](size_t index)const
		{
			return reference(state_, startIndex_ + static_cast<int>(index));
		}
		size_t size()const
		{
			return endIndex_ - startIndex_;
		}
	private:
		lua_State* state_;
		int startIndex_;
		int endIndex_;
	};
	template<> struct lua_type_traits<VariadicArgType>
	{
		typedef VariadicArgType get_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return true;
		}
		static bool checkType(lua_State* l, int index)
		{
			return true;
		}
		static get_type get(lua_State* l, int index)
		{
			return VariadicArgType(l, index);
		}
	};

	namespace nativefunction
	{
		static const int MAX_OVERLOAD_SCORE = 255;
		template<typename Fn>uint8_t compute_function_matching_score(lua_State* state, const Fn& fn)
		{
			int argcount = lua_gettop(state);

			if (strictCheckArgTypes(state, fn))
			{
				int fnargcount = argCount(fn);
				if (argcount == fnargcount)
				{
					return MAX_OVERLOAD_SCORE;
				}
				else
				{
					return std::max(100 - std::abs(argcount - fnargcount), 51);
				}
			}
			else if (checkArgTypes(state, fn))
			{
				int fnargcount = argCount(fn);
				if (argcount == fnargcount)
				{
					return 200;
				}
				else
				{
					return std::max(50 - std::abs(argcount - fnargcount), 1);
				}
			}
			else
			{
				return 0;
			}
		}

		struct BaseInvoker
		{
			virtual int argsCount()const = 0;
			virtual bool checktype(lua_State *state, bool strictcheck) = 0;
			virtual int invoke(lua_State *state) = 0;
			virtual std::string argumentTypeNames() = 0;
			virtual uint8_t argsMatchingScore(lua_State* state) = 0;
			virtual ~BaseInvoker() {}
		};

		//! deperecated
		struct FunctorType :standard::shared_ptr<BaseInvoker>
		{
			typedef standard::shared_ptr<BaseInvoker> base_ptr_;
			FunctorType() {}
			FunctorType(const standard::shared_ptr<BaseInvoker>& ptr) :base_ptr_(ptr) {}

			template<typename T>FunctorType(T f) : standard::shared_ptr<BaseInvoker>(create(f))
			{
			}
		private:
			template<typename F>
			struct FunInvoker :BaseInvoker {
				typedef F func_type;
				func_type func_;
				FunInvoker(func_type fun) :func_(fun) {}
				virtual int argsCount()const {
					return argCount(func_);
				}
				virtual bool checktype(lua_State *state, bool strictcheck) {
					if (strictcheck)
					{
						return strictCheckArgTypes(state, func_);
					}
					else
					{
						return checkArgTypes(state, func_);
					}
				}
				virtual int invoke(lua_State *state)
				{
					int count = call(state, func_);

					return count;

				}
				virtual std::string argumentTypeNames() {
					return argTypesName(func_);
				}
				virtual uint8_t argsMatchingScore(lua_State* state) {
					return compute_function_matching_score(state, func_);
				}
			};
			template<typename F>
			static base_ptr_ create(F fun)
			{
				KAGUYA_STATIC_ASSERT(nativefunction::is_callable<typename standard::decay<F>::type>::value, "argument need callable");

				typedef FunInvoker<F> InvokerType;
				return base_ptr_(new InvokerType(fun));
			}
		};


		inline FunctorType* pick_match_function(lua_State *l)
		{
			int overloadnum = int(lua_tonumber(l, lua_upvalueindex(1)));

			if (overloadnum == 1)
			{
				FunctorType* fun = static_cast<FunctorType*>(lua_touserdata(l, lua_upvalueindex(2)));

				//skip type check for performance
//				if (!fun || !(*fun) || !(*fun)->checktype(l, false))
//				{
//					return 0;
//				}
				return fun;
			}
			FunctorType* match_function = 0;
			uint8_t match_score = 0;
			for (int i = 0; i < overloadnum; ++i)
			{
				FunctorType* fun = static_cast<FunctorType*>(lua_touserdata(l, lua_upvalueindex(i + 2)));
				if (!fun || !(*fun))
				{
					continue;
				}
				uint8_t score = (*fun)->argsMatchingScore(l);
				if (match_score < score)
				{
					match_function = fun;
					match_score = score;
					if (match_score == MAX_OVERLOAD_SCORE)
					{
						return match_function;
					}
				}
			}
			return match_function;
		}



		inline std::string argmentTypes(lua_State *state)
		{
			int top = lua_gettop(state);

			std::string result = "";

			for (int i = 1; i <= top; i++) {
				if (i != 1)
				{
					result += ",";
				}


				ObjectWrapperBase* object = object_wrapper(state, i);
				if (object)
				{
					result += object->type().name();
				}
				else
				{
					result += lua_typename(state, lua_type(state, i));
				}
			}
			return result;
		}

		inline std::string build_arg_error_message(lua_State *l)
		{
			std::string message = "Argument mismatch:" + argmentTypes(l) + "\t candidate is:\n";

			int overloadnum = int(lua_tonumber(l, lua_upvalueindex(1)));
			for (int i = 0; i < overloadnum; ++i)
			{
				FunctorType* fun = static_cast<FunctorType*>(lua_touserdata(l, lua_upvalueindex(i + 2)));
				if (!fun || !(*fun))
				{
					continue;
				}
				message += std::string("\t\t") + (*fun)->argumentTypeNames() + "\n";
			}
			return message;
		}
		inline int functor_dispatcher(lua_State *l)
		{
			FunctorType* fun = pick_match_function(l);
			if (fun && (*fun))
			{
				try {
					return (*fun)->invoke(l);
				}
				catch (LuaTypeMismatch &) {
					util::traceBack(l, (std::string("maybe...") + build_arg_error_message(l)).c_str());
				}
				catch (std::exception & e) {
					util::traceBack(l, e.what());
				}
				catch (...) {
					util::traceBack(l, "Unknown exception");
				}
			}
			else
			{
				util::traceBack(l, build_arg_error_message(l).c_str());
			}
			return lua_error(l);
		}

		inline int functor_destructor(lua_State *state)
		{
			FunctorType* f = class_userdata::test_userdata<FunctorType>(state, 1);
			if (f)
			{
				f->~FunctorType();
			}
			return 0;
		}
		inline void reg_functor_destructor(lua_State* state)
		{
			if (class_userdata::newmetatable<FunctorType>(state))
			{
				lua_pushcclosure(state, &functor_destructor, 0);
				lua_setfield(state, -2, "__gc");
				lua_setfield(state, -1, "__index");
			}
		}
	}

	typedef nativefunction::FunctorType FunctorType;


	typedef std::vector<FunctorType> FunctorOverloadType;



#if KAGUYA_USE_CPP11

	namespace detail
	{
		template<typename Fn, typename... Functions> void function_match_scoring(lua_State* state, uint8_t* score_array, int current_index, const Fn& fn)
		{
			score_array[current_index] = nativefunction::compute_function_matching_score(state, fn);
		}
		template<typename Fn, typename... Functions> void function_match_scoring(lua_State* state, uint8_t* score_array, int current_index, const Fn& fn, const Functions&... fns)
		{
			score_array[current_index] = nativefunction::compute_function_matching_score(state, fn);
			if (score_array[current_index] < nativefunction::MAX_OVERLOAD_SCORE)
			{
				function_match_scoring(state, score_array, current_index + 1, fns...);
			}
		}
		template<typename... Functions> int best_function_index(lua_State* state, const Functions&... fns)
		{
			static const int fncount = sizeof...(fns);
			uint8_t score[fncount] = {};
			function_match_scoring(state, score, 0, fns...);
			uint8_t best_score = 0;
			int best_score_index = -1;
			for (int i = 0; i < fncount; ++i)
			{
				if (best_score < score[i])
				{
					best_score = score[i];
					best_score_index = i;
					if (best_score == nativefunction::MAX_OVERLOAD_SCORE)
					{
						break;
					}
				}
			}
			return best_score_index;
		}
		template<typename Fn> int invoke_index(lua_State* state, int index, int current_index, const Fn& fn)
		{
			if (index == current_index)
			{
				return nativefunction::call(state, fn);
			}
			else
			{
				return -1;
			}
		}
		template<typename Fn, typename... Functions> int invoke_index(lua_State* state, int index, int current_index, const Fn& fn, const Functions&... fns)
		{
			if (index == current_index)
			{
				return nativefunction::call(state, fn);
			}
			else
			{
				return invoke_index(state, index, current_index + 1, fns...);
			}
		}

		template<typename Fun> int best_match_invoke(lua_State* state, const Fun& fn)
		{
			return nativefunction::call(state, fn);
		}

		template<typename Fun, typename... Functions> int best_match_invoke(lua_State* state, const Fun& fn, const Functions&... fns)
		{
			int index = best_function_index(state, fn, fns...);
			if (index >= 0)
			{
				return invoke_index(state, index, 0, fn, fns...);
			}
			else
			{
				throw LuaTypeMismatch("type mismatch!!");
			}
			return 0;
		}

		template<typename TupleType, std::size_t ...S> int invoke_tuple_impl(lua_State* state, TupleType&& tuple, nativefunction::cpp11impl::index_tuple<S...>)
		{
			return best_match_invoke(state, std::get<S>(tuple)...);
		}
		template<typename TupleType> int invoke_tuple(lua_State* state, TupleType&& tuple)
		{
			typedef typename std::decay<TupleType>::type ttype;

			typedef typename nativefunction::cpp11impl::index_range<0, std::tuple_size<ttype>::value>::type indexrange;

			return invoke_tuple_impl(state, tuple, indexrange());
		}

		template<typename Fun> std::string arg_typename(const Fun& fn)
		{
			return nativefunction::argTypesName(fn);
		}

		template<typename Fun, typename... Functions> std::string arg_typename(const Fun& fn, const Functions&... fns)
		{
			return "\t\t" + nativefunction::argTypesName(fn) + "\n" + arg_typename(fns...);
		}
		template<typename TupleType, std::size_t ...S> std::string arg_typename_tuple_impl(TupleType&& tuple, nativefunction::cpp11impl::index_tuple<S...>)
		{
			return arg_typename(std::get<S>(tuple)...);
		}
		template<typename TupleType> std::string arg_typename_tuple(TupleType&& tuple)
		{
			typedef typename std::decay<TupleType>::type ttype;
			typedef typename nativefunction::cpp11impl::index_range<0, std::tuple_size<ttype>::value>::type indexrange;

			return arg_typename_tuple_impl(tuple, indexrange());
		}
	}


#else

	namespace detail
	{
#define KAGUYA_FUNCTION_SCOREING(N) if (currentbestscore < nativefunction::MAX_OVERLOAD_SCORE) {\
		int score = nativefunction::compute_function_matching_score(state, standard::get<N - 1>(tuple));\
		if (currentbestscore < score) {\
			currentbestscore = score;\
			currentbestindex = N;\
		}\
		}
#define KAGUYA_FUNCTION_INVOKE(N) if (currentbestindex == N) {\
			 return nativefunction::call(state,  standard::get<N - 1>(tuple));\
		}\


#define KAGUYA_ARG_TYPENAMES(N) + "\t\t" + nativefunction::argTypesName(standard::get<N-1>(tuple)) + "\n"
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>
#define KAGUYA_TUPLE_INVOKE_DEF(N) \
		KAGUYA_TEMPLATE_PARAMETER(N)\
		int invoke_tuple(lua_State* state, standard::tuple<KAGUYA_PP_TEMPLATE_ARG_REPEAT(N)>& tuple)\
		{\
			if(N==1){return nativefunction::call(state,  standard::get<0>(tuple));}\
			int32_t currentbestscore = 0;\
			int32_t currentbestindex = -1;\
			KAGUYA_PP_REPEAT(N, KAGUYA_FUNCTION_SCOREING);\
			KAGUYA_PP_REPEAT(N, KAGUYA_FUNCTION_INVOKE);\
			throw LuaTypeMismatch("type mismatch!!"); \
		}\
		KAGUYA_TEMPLATE_PARAMETER(N)\
		std::string arg_typename_tuple(standard::tuple<KAGUYA_PP_TEMPLATE_ARG_REPEAT(N)>& tuple)\
		{\
			return std::string() KAGUYA_PP_REPEAT(N, KAGUYA_ARG_TYPENAMES);\
		}

		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_TUPLE_INVOKE_DEF)
#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_TUPLE_INVOKE_DEF
#undef KAGUYA_ARG_TYPENAMES
#undef KAGUYA_FUNCTION_INVOKE
#undef KAGUYA_FUNCTION_SCOREING

		template<typename TupleType> int invoke_tuple(lua_State* state, TupleType& tuple)
		{
			return 0;
		}

		template<typename TupleType> std::string arg_typename_tuple(TupleType& tuple)
		{
			return "";
		}
	}
#endif



	template<typename FunctionTuple>
	struct FunctionInvokerType
	{
		FunctionTuple functions;
		FunctionInvokerType(const FunctionTuple& t) :functions(t) {}
	};
	template<typename T>
	inline FunctionInvokerType<standard::tuple<T> > function(T f)
	{
		KAGUYA_STATIC_ASSERT(nativefunction::is_callable<typename standard::decay<T>::type>::value, "argument need callable");
		return FunctionInvokerType<standard::tuple<T> >(standard::tuple<T>(f));
	}

	template<typename FTYPE, typename T>
	inline FunctionInvokerType<standard::tuple<standard::function<FTYPE> > > function(T f)
	{
		return FunctionInvokerType<standard::tuple<standard::function<FTYPE> > >(standard::tuple<standard::function<FTYPE> >(standard::function<FTYPE>(f)));
	}
#if KAGUYA_USE_CPP11


	template<typename... Functions>
	FunctionInvokerType<standard::tuple<Functions...> > overload(Functions... fns)
	{
		return FunctionInvokerType<standard::tuple<Functions...> >(standard::tuple<Functions...>(fns...));
	}
#else
#define KAGUYA_DEF_TEMPLATE(N) KAGUYA_PP_CAT(typename F,N)
#define KAGUYA_TEMPLATE_ARG_DEF(N) KAGUYA_PP_CAT(F,N)
#define KAGUYA_TUPLE_ARG_DEF(N) KAGUYA_PP_CAT(f,N)
#define KAGUYA_ARG_DEF(N) KAGUYA_PP_CAT(F,N) KAGUYA_PP_CAT(f,N)
#define KAGUYA_FOVERLOAD_DEF(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_DEF_TEMPLATE)>\
		FunctionInvokerType<standard::tuple<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_TEMPLATE_ARG_DEF)> > overload(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_ARG_DEF))\
		{\
			typedef typename standard::tuple<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_TEMPLATE_ARG_DEF)> ttype;\
			return FunctionInvokerType<ttype>(ttype(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_TUPLE_ARG_DEF)));\
		}
	KAGUYA_PP_REPEAT_DEF(9, KAGUYA_FOVERLOAD_DEF)
#undef KAGUYA_DEF_TEMPLATE
#undef KAGUYA_TEMPLATE_ARG_DEF
#undef KAGUYA_TUPLE_ARG_DEF
#undef KAGUYA_ARG_DEF
#undef KAGUYA_FOVERLOAD_DEF
#endif


	template<typename FunctionTuple> struct lua_type_traits<FunctionInvokerType<FunctionTuple> >
	{
		typedef FunctionInvokerType<FunctionTuple> userdatatype;
		typedef const FunctionInvokerType<FunctionTuple>& push_type;


		static std::string build_arg_error_message(lua_State *state, FunctionTuple* tuple)
		{
			return  "Argument mismatch:" + nativefunction::argmentTypes(state) + "\t candidate is:\n"
				+ detail::arg_typename_tuple(*tuple);
		}

		static int invoke(lua_State *state)
		{
			FunctionTuple* t = static_cast<FunctionTuple*>(lua_touserdata(state, lua_upvalueindex(1)));

			if (t)
			{
				try {
					return detail::invoke_tuple(state, *t);
				}
				catch (LuaTypeMismatch &) {
					util::traceBack(state, (std::string("maybe...") + build_arg_error_message(state, t)).c_str());
				}
				catch (std::exception & e) {
					util::traceBack(state, e.what());
				}
				catch (...) {
					util::traceBack(state, "Unknown exception");
				}
			}
			return lua_error(state);
		}

		inline static int tuple_destructor(lua_State *state)
		{
			FunctionTuple* f = static_cast<FunctionTuple*>(lua_touserdata(state, 1));
			if (f)
			{
				f->~FunctionTuple();
			}
			return 0;
		}

		static int push(lua_State* state, push_type fns)
		{
			void* ptr = lua_newuserdata(state, sizeof(FunctionTuple));
			new(ptr) FunctionTuple(fns.functions);
			lua_createtable(state, 0, 2);
			lua_pushcclosure(state, &tuple_destructor, 0);
			lua_setfield(state, -2, "__gc");
			lua_pushvalue(state, -1);
			lua_setfield(state, -1, "__index");
			lua_setmetatable(state, -2);
			lua_pushcclosure(state, &invoke, 1);

			return 1;
		}
	};


	//!deperecated
	template<>	struct lua_type_traits<FunctorType> {
		typedef FunctorType get_type;
		typedef FunctorType push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return 0 != class_userdata::test_userdata<FunctorType>(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return 0 != class_userdata::test_userdata<FunctorType>(l, index);
		}
		static FunctorType get(lua_State* l, int index)
		{
			FunctorType* ptr = class_userdata::test_userdata<FunctorType>(l, index);
			if (ptr)
			{
				return *ptr;
			}
			return FunctorType();
		}
		static int push(lua_State* l, const FunctorType& f)
		{
			lua_pushnumber(l, 1);//no overload
			void *storage = lua_newuserdata(l, sizeof(FunctorType));
			new(storage) FunctorType(f);
			class_userdata::setmetatable<FunctorType>(l);
			lua_pushcclosure(l, &nativefunction::functor_dispatcher, 2);
			return 1;
		}
#if KAGUYA_USE_RVALUE_REFERENCE
		static int push(lua_State* l, FunctorType&& f)
		{
			lua_pushnumber(l, 1);//no overload
			void *storage = lua_newuserdata(l, sizeof(FunctorType));
			new(storage) FunctorType(std::forward<FunctorType>(f));
			class_userdata::setmetatable<FunctorType>(l);
			lua_pushcclosure(l, &nativefunction::functor_dispatcher, 2);
			return 1;
		}
#endif
	};

	template<>	struct lua_type_traits<const FunctorType&> :lua_type_traits<FunctorType> {};

	//specialize for c function
	template<typename T> struct lua_type_traits < T
		, typename traits::enable_if<traits::is_function<typename traits::remove_pointer<T>::type>::value>::type >
	{
		static int push(lua_State* l, T f)
		{
			return util::one_push(l, function(f));
		}
	};



	template<>	struct lua_type_traits<FunctorOverloadType> {
		typedef const FunctorOverloadType& push_type;

		static int push(lua_State* l, push_type fns)
		{
			lua_pushnumber(l, static_cast<lua_Number>(fns.size()));
			for (FunctorOverloadType::const_iterator f = fns.begin(); f != fns.end(); ++f)
			{
				void *storage = lua_newuserdata(l, sizeof(FunctorType));
				new(storage) FunctorType(*f);
				class_userdata::setmetatable<FunctorType>(l);
			}
			lua_pushcclosure(l, &nativefunction::functor_dispatcher, static_cast<int>(fns.size() + 1));
			return 1;
		}
	};


	template<typename T> struct lua_type_traits<standard::function<T> > {
		typedef const standard::function<T>& push_type;
		typedef standard::function<T> get_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TFUNCTION;
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_type(l, index) == LUA_TFUNCTION;
		}
		static get_type get(lua_State* l, int index)
		{
			if (lua_type(l, index) != LUA_TFUNCTION) {
				return get_type();
			}
			lua_pushvalue(l, index);
			return get_type(LuaFunction(l, StackTop()));
		}

		static int push(lua_State* l, push_type v)
		{
			return util::one_push(l, function(v));
		}
	};
}

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>


// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <string>
#include <vector>
#include <map>
#include <typeinfo>
#include <iostream>



// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <vector>
#include <map>
#include <cassert>



namespace kaguya
{
	class FunctionResults :public Ref::StackRef, public LuaVariantImpl<FunctionResults>
	{
		FunctionResults(lua_State* state,int return_status, int startIndex) :Ref::StackRef(state, startIndex, true),state_(state), resultStatus_(return_status), resultCount_(lua_gettop(state) + 1 - startIndex)
		{
		}
		FunctionResults(lua_State* state,int return_status, int startIndex, int endIndex) :Ref::StackRef(state, startIndex, true), state_(state), resultStatus_(return_status), resultCount_(endIndex - startIndex)
		{
		}
		friend class FunctionResultProxy;
	public:
		FunctionResults() :Ref::StackRef(0, 0, true), state_(0), resultCount_(0)
		{
		}
		FunctionResults(lua_State* state) :Ref::StackRef(state, 0, true), state_(state), resultCount_(0)
		{
		}
#if KAGUYA_USE_CPP11
		FunctionResults(FunctionResults&&src) : Ref::StackRef(std::move(src)), state_(src.state_), resultCount_(src.resultCount_)
		{
			src.state_ = 0;
		}
#else
		FunctionResults(const FunctionResults&src) : Ref::StackRef(src), state_(src.state_), resultCount_(src.resultCount_)
		{
			src.state_ = 0;
		}
#endif

		~FunctionResults()
		{
		}

		struct reference :public Ref::StackRef, public LuaVariantImpl<reference>
		{
			reference(lua_State* s, int index) :Ref::StackRef(s, index, false)
			{
			}

			const reference* operator->()const
			{
				return this;
			}
		};
		struct iterator
		{
			iterator(lua_State* s, int i) : state(s), stack_index(i)
			{
			}
			reference operator*()const
			{
				return reference(state, stack_index);
			}
			reference operator->()const
			{
				return reference(state, stack_index);
			}
			const iterator& operator++()
			{
				stack_index++;
				return *this;
			}
			iterator operator++(int)
			{
				return iterator(state, stack_index++);
			}

			iterator operator+=(int n)
			{
				stack_index += n;
				return iterator(state, stack_index);
			}
			bool operator==(const iterator& other)const
			{
				return state == other.state && stack_index == other.stack_index;
			}
			bool operator!=(const iterator& other)const
			{
				return !(*this == other);
			}
			int index() const { return stack_index; }
		private:
			lua_State* state;
			int stack_index;
		};
		typedef iterator const_iterator;
		typedef reference const_reference;

		iterator begin()
		{
			return iterator(state_, stack_index_);
		}
		iterator end()
		{
			return iterator(state_, stack_index_ + resultCount_);
		}
		const_iterator begin()const
		{
			return const_iterator(state_, stack_index_);
		}
		const_iterator end()const
		{
			return const_iterator(state_, stack_index_ + resultCount_);
		}
		const_iterator cbegin()const
		{
			return const_iterator(state_, stack_index_);
		}
		const_iterator cend()const
		{
			return const_iterator(state_, stack_index_ + resultCount_);
		}

		
		template<class Result>
		Result get_result()const
		{
			return util::get_result<Result>(state_, stack_index_);
		}
		lua_State* state()const { return state_; }

		template<typename T>
		typename lua_type_traits<T>::get_type result_at(size_t index)const
		{
			if (index >= result_size())
			{
				throw std::out_of_range("function result out of range");
			}
			return lua_type_traits<T>::get(state_, stack_index_ + static_cast<int>(index));
		}
		reference result_at(size_t index)const
		{
			if (index >= result_size())
			{
				throw std::out_of_range("function result out of range");
			}
			return reference(state_, stack_index_ + static_cast<int>(index));
		}

		size_t result_size()const
		{
			return resultCount_;
		}

		size_t resultStatus()const
		{
			return resultStatus_;
		}

	private:
		mutable lua_State* state_;
		int resultStatus_;
		int resultCount_;

	};

	template<typename RetType>
	inline RetType FunctionResultProxy::ReturnValue(lua_State* state, int return_status, int retindex, types::typetag<RetType> tag)
	{
		return FunctionResults(state, return_status, retindex).get_result<RetType>();
	}
	inline FunctionResults FunctionResultProxy::ReturnValue(lua_State* state, int return_status, int retindex, types::typetag<FunctionResults> tag)
	{
		return FunctionResults(state, return_status, retindex);
	}
	inline void FunctionResultProxy::ReturnValue(lua_State* state, int return_status, int retindex, types::typetag<void> tag)
	{
		FunctionResults(state, return_status, retindex);
	}

#if KAGUYA_USE_CPP11
	template<typename Derived>template<class...Args>
	FunctionResults LuaFunctionImpl<Derived>::operator()(Args&&... args)
	{
		return this->template call<FunctionResults>(std::forward<Args>(args)...);
	}

	template<typename Derived>template<class...Args>
	FunctionResults LuaThreadImpl<Derived>::operator()(Args&&... args)
	{
		return this->template resume<FunctionResults>(std::forward<Args>(args)...);
	}
	template<typename Derived>template<class...Args>
	FunctionResults LuaVariantImpl<Derived>::operator()(Args&&... args)
	{
		int t = type();
		if (t == LUA_TTHREAD)
		{
			return this->template resume<FunctionResults>(std::forward<Args>(args)...);
		}
		else if (t == LUA_TFUNCTION)
		{
			return this->template call<FunctionResults>(std::forward<Args>(args)...);
		}
		else
		{
			except::typeMismatchError(state_(), " is not function or thread");
			return FunctionResults(state_());
		}
	}
#else
#define KAGUYA_TEMPLATE_PARAMETER(N)template<typename Derived>
#define KAGUYA_FUNCTION_ARGS_DEF(N)
#define KAGUYA_CALL_ARGS(N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)

#define KAGUYA_OP_FN_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline FunctionResults LuaFunctionImpl<Derived>::operator()(KAGUYA_FUNCTION_ARGS_DEF(N))\
	{\
			return this->template call<FunctionResults>(KAGUYA_CALL_ARGS(N)); \
	}\
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline FunctionResults LuaThreadImpl<Derived>::operator()(KAGUYA_FUNCTION_ARGS_DEF(N))\
	{\
			return this->template resume<FunctionResults>(KAGUYA_CALL_ARGS(N)); \
	}\
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline FunctionResults LuaVariantImpl<Derived>::operator()(KAGUYA_FUNCTION_ARGS_DEF(N))\
	{\
			int t = type();\
			if (t == LUA_TTHREAD)\
			{\
				return this->template resume<FunctionResults>(KAGUYA_CALL_ARGS(N)); \
			}\
			else if (t == LUA_TFUNCTION)\
			{\
				return this->template call<FunctionResults>(KAGUYA_CALL_ARGS(N)); \
			}\
			else\
			{\
				except::typeMismatchError(state_(), " is not function or thread");\
				return FunctionResults(state_());\
			}\
	}

	KAGUYA_OP_FN_DEF(0)

#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_CALL_ARGS
#define KAGUYA_TEMPLATE_PARAMETER(N) template<typename Derived> template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>
#define KAGUYA_FUNCTION_ARGS_DEF(N) KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG)
#define KAGUYA_CALL_ARGS(N) KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PUSH_ARG_DEF)

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PUSH_ARG_DEF(N) KAGUYA_PP_CAT(a,N) 

		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_OP_FN_DEF)
#undef KAGUYA_OP_FN_DEF
#undef KAGUYA_TEMPLATE_PARAMETER

#undef KAGUYA_CALL_ARGS
#undef KAGUYA_FUNCTION_ARGS_DEF
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_CALL_DEF
#undef KAGUYA_OP_FN_DEF
#endif

	inline std::ostream& operator<<(std::ostream& os, const FunctionResults& res)
	{
		for (FunctionResults::const_iterator it = res.begin(); it != res.end(); ++it)
		{
			if (it != res.begin())
			{
				os << ",";
			}
			util::stackValueDump(os, res.state(), it.index());
		}

		return os;
	}


	template<>	struct lua_type_traits<FunctionResults> {
		static int push(lua_State* l, const FunctionResults& ref)
		{
			int size = 0;
			for (FunctionResults::const_iterator it = ref.cbegin(); it != ref.cend(); ++it)
			{
				size += it->push(l);
			}
			return size;
		}
	};

	template<>	struct lua_type_traits<FunctionResults::reference> {
		static int push(lua_State* l, const FunctionResults::reference& ref)
		{
			return ref.push(l);
		}
	};
	template <unsigned int I>
	FunctionResults::reference get(const FunctionResults& res) { return res.result_at(I); }

	template<>	struct lua_type_traits<LuaFunction> {
		typedef LuaFunction get_type;
		typedef LuaFunction push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_isfunction(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isfunction(l, index) || lua_isnil(l, index);
		}
		static LuaFunction get(lua_State* l, int index)
		{
			lua_pushvalue(l, index);
			return LuaFunction(l, StackTop());
		}
		static int push(lua_State* l, const LuaFunction& ref)
		{
			ref.push(l);
			return 1;
		}
	};
	template<>	struct lua_type_traits<const LuaFunction&> :lua_type_traits<LuaFunction> {};

	template<>	struct lua_type_traits<LuaThread> {
		typedef LuaThread get_type;
		typedef LuaThread push_type;

		static bool strictCheckType(lua_State* l, int index)
		{
			return lua_isthread(l, index);
		}
		static bool checkType(lua_State* l, int index)
		{
			return lua_isthread(l, index) || lua_isnil(l, index);
		}
		static LuaThread get(lua_State* l, int index)
		{
			lua_pushvalue(l, index);
			return LuaThread(l, StackTop());
		}
		static int push(lua_State* l, const LuaThread& ref)
		{
			ref.push(l);
			return 1;
		}
	};
	template<>	struct lua_type_traits<const LuaThread&> :lua_type_traits<LuaThread> {};



	/**
	* @brief table and function binder.
	* state["table"]->*"fun"() is table:fun() in Lua
	* @param arg... function args
	*/
	class mem_fun_binder
	{
	public:
		template<class T>
		mem_fun_binder(LuaRef self, T key) :self_(self), fun_(self_.getField(key))
		{
		}

#define KAGUYA_DELEGATE_LUAREF fun_
#define KAGUYA_DELEGATE_FIRST_ARG self_
#ifndef KAGUYA_DELEGATE_FIRST_ARG
#define KAGUYA_DELEGATE_FIRST_ARG
#define KAGUYA_DELEGATE_FIRST_ARG_C
#else
#define KAGUYA_DELEGATE_FIRST_ARG_C KAGUYA_DELEGATE_FIRST_ARG,
#endif

#if KAGUYA_USE_CPP11
		template<class... Args>
		FunctionResults operator()(Args&&... args)
		{
			return KAGUYA_DELEGATE_LUAREF(KAGUYA_DELEGATE_FIRST_ARG_C std::forward<Args>(args)...);
		}

		template<class Result, class... Args>
		Result call(Args&&... args)
		{
			return KAGUYA_DELEGATE_LUAREF.call<Result>(KAGUYA_DELEGATE_FIRST_ARG_C std::forward<Args>(args)...);
		}
#else

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)
#define KAGUYA_PUSH_ARG_DEF(N) KAGUYA_PP_CAT(a,N)
#define KAGUYA_OP_FN_DEF(N) \
		template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)> \
		FunctionResults operator()(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG))\
		{\
			return KAGUYA_DELEGATE_LUAREF(KAGUYA_DELEGATE_FIRST_ARG_C KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PUSH_ARG_DEF));\
		}

		/**
		* @name operator()
		* @brief If type is function, call lua function.
		If type is lua thread,start or resume lua thread.
		Otherwise send error message to error handler
		* @param arg... function args
		*/
		//@{
		FunctionResults operator()()
		{
			return KAGUYA_DELEGATE_LUAREF(KAGUYA_DELEGATE_FIRST_ARG);
		}
		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_OP_FN_DEF)
		//@}

#define KAGUYA_CALL_DEF(N) \
		template<class Result,KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)> \
		Result call(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG))\
		{\
			return KAGUYA_DELEGATE_LUAREF.call<Result>(KAGUYA_DELEGATE_FIRST_ARG_C KAGUYA_PP_REPEAT_ARG(N, KAGUYA_PUSH_ARG_DEF));\
		}

		template<class Result>
		Result call()
		{
			return KAGUYA_DELEGATE_LUAREF.call<Result>(KAGUYA_DELEGATE_FIRST_ARG);
		}
		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_CALL_DEF)

#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_PUSH_ARG_DEF
#undef KAGUYA_OP_FN_DEF
#undef KAGUYA_CALL_DEF
#undef KAGUYA_RESUME_DEF
#endif
			
#undef KAGUYA_DELEGATE_FIRST_ARG
#undef KAGUYA_DELEGATE_FIRST_ARG_C

#undef KAGUYA_DELEGATE_FIRST_ARG
#undef KAGUYA_DELEGATE_LUAREF

	private:
		LuaRef self_;//Table or Userdata
		LuaFunction fun_;
	};
	/*
	inline mem_fun_binder LuaRef::operator->*(const char* key)
	{
		return mem_fun_binder(*this, key);
	}*/
}

namespace kaguya
{
	template<class A1, class A2 = void
		, class A3 = void, class A4 = void, class A5 = void
		, class A6 = void, class A7 = void, class A8 = void,
		class A9 = void>struct MultipleBase {
	};
}

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


namespace kaguya
{


	//!ClassMetatable is deprecated. use UserdataMetatable instead.
	template<typename class_type, typename base_class_type = void>
	struct ClassMetatable
	{

		struct DataHolderBase
		{
			virtual int push_to_lua(lua_State* data)const = 0;
			virtual ~DataHolderBase() {}
		};
		template<typename T>
		struct DataHolder :DataHolderBase
		{
			DataHolder(const T& d) :data(d) {}
			T data;
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<T>::push(state, data);
			}
		};
		template<int N>
		struct DataHolder<char[N]> :DataHolderBase {
			std::string data;
			DataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
		template<int N>
		struct DataHolder<const char[N]> :DataHolderBase {
			std::string data;
			DataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
#if KAGUYA_USE_CPP11
		template<typename T>
		struct MoveDataHolder :DataHolderBase
		{
			MoveDataHolder(T&& d) :data(std::move(d)) {}
			T data;
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<T>::push(state, std::move(data));
			}
		};
		template<int N>
		struct MoveDataHolder<char[N]> :DataHolderBase {
			std::string data;
			MoveDataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
		template<int N>
		struct MoveDataHolder<const char[N]> :DataHolderBase {
			std::string data;
			MoveDataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
#endif
		typedef standard::shared_ptr<DataHolderBase> DataHolderType;
		template<typename T>DataHolderType makeDataHolder(const T& d)
		{
			return DataHolderType(new DataHolder<T>(d));
		}

		typedef std::vector<FunctorType> FunctorOverloadType;
		typedef std::map<std::string, FunctorOverloadType> FuncMapType;
		typedef std::map<std::string, FunctorType> PropMapType;

		typedef std::map<std::string, DataHolderType> MemberMapType;
		typedef std::map<std::string, std::string> CodeChunkMapType;


		ClassMetatable()
		{
			addStaticFunction("__gc", &class_userdata::destructor<ObjectWrapperBase>);

			KAGUYA_STATIC_ASSERT(is_registerable<class_type>::value || !traits::is_std_vector<class_type>::value, "std::vector is binding to lua-table by default.If you wants register for std::vector yourself,"
				"please define KAGUYA_NO_STD_VECTOR_TO_TABLE");

			KAGUYA_STATIC_ASSERT(is_registerable<class_type>::value || !traits::is_std_map<class_type>::value, "std::map is binding to lua-table by default.If you wants register for std::map yourself,"
				"please define KAGUYA_NO_STD_MAP_TO_TABLE");

			//can not register push specialized class
			KAGUYA_STATIC_ASSERT(is_registerable<class_type>::value,
				"Can not register specialized of type conversion class. e.g. std::tuple");
		}

		LuaRef registerClass(lua_State* state)const
		{
			util::ScopedSavedStack save(state);
			if (class_userdata::newmetatable<class_type>(state))
			{
				LuaTable metatable(state, StackTop());
				metatable.push();
				registerMember(state);

				if (!traits::is_same<base_class_type, void>::value || !property_map_.empty())//if base class has property and derived class hasnt property. need property access metamethod
				{
					for (PropMapType::const_iterator it = property_map_.begin(); it != property_map_.end(); ++it)
					{
						int count = lua_type_traits<FunctorType>::push(state, it->second);
						if (count > 1)
						{
							lua_pop(state, count - 1);
							count = 1;
						}
						if (count == 1)
						{
							lua_setfield(state, -2, ("_prop_" + it->first).c_str());
						}
					}
					LuaFunction indexfun = kaguya::LuaFunction::loadstring(state, "local arg = {...};local metatable = arg[1];"
						"return function(table, index)"
						//						" if type(table) == 'userdata' then "
						" local propfun = metatable['_prop_'..index];"
						" if propfun then return propfun(table) end "
						//						" end "
						" return metatable[index]"
						" end")(metatable);

					metatable.setField("__index", indexfun);

					LuaFunction newindexfn = LuaFunction::loadstring(state, "local arg = {...};local metatable = arg[1];"
						" return function(table, index, value) "
						" if type(table) == 'userdata' then "
						" local propfun = metatable['_prop_'..index];"
						" if propfun then return propfun(table,value) end "
						" end "
						" rawset(table,index,value) "
						" end")(metatable);
					metatable.setField("__newindex", newindexfn);
				}
				else
				{
					metatable.setField("__index", metatable);
				}

				set_base_metatable(state, metatable, types::typetag<base_class_type>());

				return metatable;
			}
			else
			{
				except::OtherError(state, typeid(class_type*).name() + std::string(" is already registered"));
			}
			return LuaRef(state);
		}

#if KAGUYA_USE_CPP11
		template<typename... Args>
		ClassMetatable& addConstructor()
		{
			typedef typename nativefunction::constructor_signature_type<class_type, Args...> cons;
			function_map_["new"].push_back(cons());
			return *this;
		}
#else
#define KAGUYA_TEMPLATE_PARAMETER(N)
#define KAGUYA_ADD_CON_FN_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline ClassMetatable& addConstructor()\
	{\
		typedef typename nativefunction::constructor_signature_type<class_type KAGUYA_PP_TEMPLATE_ARG_REPEAT_CONCAT(N)> cons;\
		function_map_["new"].push_back(cons());\
		return *this;\
	}
		KAGUYA_ADD_CON_FN_DEF(0)
#undef KAGUYA_TEMPLATE_PARAMETER
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_ADD_CON_FN_DEF)
#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_ADD_CON_FN_DEF

#endif
			//variadic arguments constructor(receive const std::vector<LuaRef>&)
			//		template<>ClassMetatable& addConstructor(types::typetag<VariadicArgType>* )
			ClassMetatable& addConstructorVariadicArg()
		{
			typedef typename nativefunction::constructor_signature_type<class_type, VariadicArgType> cons;
			function_map_["new"].push_back(FunctorType(cons()));
			return *this;
		}

#if defined(_MSC_VER) && _MSC_VER <= 1800
		//deprecated
		//can not write  Ret class_type::* f on MSC++2013
		template<typename Fun>
		KAGUYA_DEPRECATED_FEATURE("ClassMetatable is deprecated. use UserdataMetatable instead.") ClassMetatable& addMember(const char* name, Fun f)
		{
			return addMemberFunction(name, f);
		}
		//deprecated
		//can not write  Ret class_type::* f on MSC++2013
		template<typename Fun>
		ClassMetatable& addMemberFunction(const char* name, Fun f)
		{
			if (has_key(name, true))
			{
				//already registerd
				return *this;
			}
			addFunction(name, f);
			return *this;
		}
#else
		//deprecated
		template<typename Ret>
		KAGUYA_DEPRECATED_FEATURE("ClassMetatable is deprecated. use UserdataMetatable instead.") ClassMetatable& addMember(const char* name, Ret class_type::* f)
		{
			return addMemberFunction(name, f);
		}
		template<typename Ret>
		ClassMetatable& addMemberFunction(const char* name, Ret class_type::* f)
		{
			if (has_key(name, true))
			{
				//already registerd
				return *this;
			}
			addFunction(name, f);
			return *this;
		}
#endif
		//add member property
		template<typename Ret>
		ClassMetatable& addProperty(const char* name, Ret class_type::* mem)
		{
			property_map_[name] = FunctorType(mem);
			return *this;
		}


		//! deprecated
		template<typename Fun>
		ClassMetatable& addStaticMember(const char* name, Fun f)
		{
			return addStaticFunction(name, f);
		}
		//! deprecated
		template<typename Fun>
		ClassMetatable& addStaticFunction(const char* name, Fun f)
		{
			if (has_key(name, true))
			{
				//already registerd
				return *this;
			}
			addFunction(name, f);
			return *this;
		}

		//add field to
		ClassMetatable& addCodeChunkResult(const char* name, const std::string& lua_code_chunk)
		{
			code_chunk_map_[name] = lua_code_chunk;
			return *this;
		}


		template<typename Data>
		ClassMetatable& addStaticField(const char* name, const Data& f)
		{
			if (has_key(name))
			{
				//already registerd
				return *this;
			}
			member_map_[name] = DataHolderType(new DataHolder<Data>(f));
			return *this;
		}


#if KAGUYA_USE_CPP11
		template<typename Data>
		ClassMetatable& addStaticField(const char* name, Data&& f)
		{
			if (has_key(name))
			{
				//already registerd
				return *this;
			}
			member_map_[name] = DataHolderType(new MoveDataHolder<Data>(std::move(f)));
			return *this;
		}
#endif

	private:

		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<void>)const
		{
		}
		template<class Base>
		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<Base>)const
		{
			class_userdata::get_metatable<Base>(state);
			metatable.setMetatable(LuaTable(state, StackTop()));

			PointerConverter& pconverter = PointerConverter::get(state);
			pconverter.add_type_conversion<Base, class_type>();
		}

		void set_multiple_base(lua_State* state, LuaTable& metatable, const LuaTable& metabases)const
		{
			LuaTable newmeta(state, NewTable());

			LuaFunction indexfun = kaguya::LuaFunction::loadstring(state, "local arg = {...};local metabases = arg[1];"
				"return function(t, k)"
				" for i = 1,#metabases do "
				" local v = metabases[i][k] "
				" if v then "
				" t[k] = v "
				" return v end "
				" end"
				" end")(metabases);

			newmeta.setField("__index", indexfun);

			metatable.setMetatable(newmeta);

		}
#if KAGUYA_USE_CPP11

		template<typename Base>
		void metatables(lua_State* state, LuaTable& metabases, PointerConverter& pvtreg, types::typetag<MultipleBase<Base> >)const
		{
			class_userdata::get_metatable<Base>(state);
			metabases.setField(metabases.size() + 1, LuaTable(state, StackTop()));
			pvtreg.add_type_conversion<Base, class_type>();
		}
		template<typename Base, typename... Remain>
		void metatables(lua_State* state, LuaTable& metabases, PointerConverter& pvtreg, types::typetag<MultipleBase<Base, Remain...> >)const
		{
			class_userdata::get_metatable<Base>(state);
			metabases.setField(metabases.size() + 1, LuaTable(state, StackTop()));
			pvtreg.add_type_conversion<Base, class_type>();
			metatables(state, metabases, pvtreg, types::typetag<MultipleBase<Remain...> >());
		}

		template<typename... Bases>
		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<MultipleBase<Bases...> > metatypes)const
		{
			PointerConverter& pconverter = PointerConverter::get(state);
			LuaTable metabases(state, NewTable(sizeof...(Bases), 0));
			metatables(state, metabases, pconverter, metatypes);
			set_multiple_base(state, metatable, metabases);
		}

#else
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>
#define KAGUYA_GET_BASE_METATABLE(N) class_userdata::get_metatable<KAGUYA_PP_CAT(A,N)>(state);\
		metabases.setField(metabases.size() + 1, LuaTable(state, StackTop())); \
		pconverter.add_type_conversion<KAGUYA_PP_CAT(A,N),class_type>();
#define KAGUYA_MULTIPLE_INHERITANCE_SETBASE_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<MultipleBase<KAGUYA_PP_TEMPLATE_ARG_REPEAT(N)> > metatypes)const\
		{\
			PointerConverter& pconverter = PointerConverter::get(state);\
			LuaTable metabases(state, NewTable(N, 0));\
			KAGUYA_PP_REPEAT(N, KAGUYA_GET_BASE_METATABLE); \
			set_multiple_base(state, metatable, metabases);\
		}\

		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_MULTIPLE_INHERITANCE_SETBASE_DEF)
#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_MULTIPLE_INHERITANCE_SETBASE_DEF
#undef KAGUYA_GET_BASE_METATABLE
#undef KAGUYA_TYPE_CHECK_REP
#endif

			bool has_key(const std::string& key, bool exclude_function = false)
		{
			if (!exclude_function && function_map_.find(key) != function_map_.end())
			{
				return true;
			}
			if (member_map_.find(key) != member_map_.end())
			{
				return true;
			}
			return false;
		}
		void registerFunction(lua_State* state, const char* name, const FunctorOverloadType& func_array)const
		{
			int count = lua_type_traits<FunctorOverloadType>::push(state, func_array);
			if (count > 1)
			{
				lua_pop(state, count - 1);
				count = 1;
			}
			if (count == 1)
			{
				lua_setfield(state, -2, name);
			}
		}
		void registerField(lua_State* state, const char* name, const DataHolderType& value)const
		{
			int count = value->push_to_lua(state);
			if (count > 1)
			{
				lua_pop(state, count - 1);
				count = 1;
			}
			if (count == 1)
			{
				lua_setfield(state, -2, name);
			}
		}
		void registerCodeChunk(lua_State* state, const char* name, std::string value)const
		{
			util::ScopedSavedStack save(state);
			int status = luaL_loadstring(state, value.c_str());
			if (!except::checkErrorAndThrow(status, state)) { return; }
			status = lua_pcall_wrap(state, 0, 1);
			if (!except::checkErrorAndThrow(status, state)) { return; }
			lua_setfield(state, -2, name);
		}

		void registerMember(lua_State* state)const
		{
			for (typename FuncMapType::const_iterator it = function_map_.begin(); it != function_map_.end(); ++it)
			{
				registerFunction(state, it->first.c_str(), it->second);
			}
			for (typename MemberMapType::const_iterator it = member_map_.begin(); it != member_map_.end(); ++it)
			{
				registerField(state, it->first.c_str(), it->second);
			}
			for (typename CodeChunkMapType::const_iterator it = code_chunk_map_.begin(); it != code_chunk_map_.end(); ++it)
			{
				registerCodeChunk(state, it->first.c_str(), it->second);
			}
		}
		template<typename Fun>
		ClassMetatable& addFunction(const char* name, Fun f)
		{
			function_map_[name].push_back(FunctorType(f));
			return *this;
		}
		FuncMapType function_map_;
		PropMapType property_map_;
		MemberMapType member_map_;
		CodeChunkMapType code_chunk_map_;
	};
}

namespace kaguya
{
	namespace metatable_detail
	{
		struct DataHolderBase
		{
			virtual int push_to_lua(lua_State* data)const = 0;
			virtual ~DataHolderBase() {}
		};
		template<typename T>
		struct DataHolder :DataHolderBase
		{
			DataHolder(const T& d) :data(d) {}
			T data;
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<T>::push(state, data);
			}
		};
		template<int N>
		struct DataHolder<char[N]> :DataHolderBase {
			std::string data;
			DataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
		template<int N>
		struct DataHolder<const char[N]> :DataHolderBase {
			std::string data;
			DataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
#if KAGUYA_USE_CPP11
		template<typename T>
		struct MoveDataHolder :DataHolderBase
		{
			MoveDataHolder(T&& d) :data(std::move(d)) {}
			T data;
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<T>::push(state, std::move(data));
			}
		};
		template<int N>
		struct MoveDataHolder<char[N]> :DataHolderBase {
			std::string data;
			MoveDataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
		template<int N>
		struct MoveDataHolder<const char[N]> :DataHolderBase {
			std::string data;
			MoveDataHolder(const std::string& d) :data(d) {}
			virtual int push_to_lua(lua_State* state)const
			{
				return lua_type_traits<std::string>::push(state, data);
			}
		};
		typedef standard::shared_ptr<DataHolderBase> DataHolderType;
		template<typename T>DataHolderType makeDataHolder(T&& d)
		{
			return DataHolderType(new MoveDataHolder<T>(std::move(d)));
		}
#endif
		typedef standard::shared_ptr<DataHolderBase> DataHolderType;
		template<typename T>DataHolderType makeDataHolder(const T& d)
		{
			return DataHolderType(new DataHolder<T>(d));
		}
	}

	template<typename class_type, typename base_class_type = void>
	class UserdataMetatable
	{
		typedef std::map<std::string, metatable_detail::DataHolderType> PropMapType;
		typedef std::map<std::string, metatable_detail::DataHolderType> MemberMapType;
		typedef std::map<std::string, std::string> CodeChunkMapType;

	public:

		UserdataMetatable()
		{
			addStaticFunction("__gc", &class_userdata::destructor<ObjectWrapperBase>);

			KAGUYA_STATIC_ASSERT(is_registerable<class_type>::value || !traits::is_std_vector<class_type>::value, "std::vector is binding to lua-table by default.If you wants register for std::vector yourself,"
				"please define KAGUYA_NO_STD_VECTOR_TO_TABLE");

			KAGUYA_STATIC_ASSERT(is_registerable<class_type>::value || !traits::is_std_map<class_type>::value, "std::map is binding to lua-table by default.If you wants register for std::map yourself,"
				"please define KAGUYA_NO_STD_MAP_TO_TABLE");

			//can not register push specialized class
			KAGUYA_STATIC_ASSERT(is_registerable<class_type>::value,
				"Can not register specialized of type conversion class. e.g. std::tuple");
		}

		LuaRef registerClass(lua_State* state)const
		{
			util::ScopedSavedStack save(state);
			if (class_userdata::newmetatable<class_type>(state))
			{
				LuaTable metatable(state, StackTop());
				metatable.push();
				registerMember(state);

				if (!traits::is_same<base_class_type, void>::value || !property_map_.empty())//if base class has property and derived class hasnt property. need property access metamethod
				{
					for (typename PropMapType::const_iterator it = property_map_.begin(); it != property_map_.end(); ++it)
					{
						int count = it->second->push_to_lua(state);
						if (count > 1)
						{
							lua_pop(state, count - 1);
							count = 1;
						}
						if (count == 1)
						{
							lua_setfield(state, -2, ("_prop_" + it->first).c_str());
						}
					}
					LuaFunction indexfun = kaguya::LuaFunction::loadstring(state, "local arg = {...};local metatable = arg[1];"
						"return function(table, index)"
						//						" if type(table) == 'userdata' then "
						" local propfun = metatable['_prop_'..index];"
						" if propfun then return propfun(table) end "
						//						" end "
						" return metatable[index]"
						" end")(metatable);

					metatable.setField("__index", indexfun);

					LuaFunction newindexfn = LuaFunction::loadstring(state, "local arg = {...};local metatable = arg[1];"
						" return function(table, index, value) "
						" if type(table) == 'userdata' then "
						" local propfun = metatable['_prop_'..index];"
						" if propfun then return propfun(table,value) end "
						" end "
						" rawset(table,index,value) "
						" end")(metatable);
					metatable.setField("__newindex", newindexfn);
				}
				else
				{
					metatable.setField("__index", metatable);
				}

				set_base_metatable(state, metatable, types::typetag<base_class_type>());

				return metatable;
			}
			else
			{
				except::OtherError(state, typeid(class_type*).name() + std::string(" is already registered"));
			}
			return LuaRef(state);
		}


#if KAGUYA_USE_CPP11
		template<typename... ArgTypes>
		UserdataMetatable& setConstructors()
		{
			member_map_["new"] = metatable_detail::makeDataHolder(overload(typename nativefunction::functionToConstructorSignature<class_type,ArgTypes>::type()...));
			return *this;
		}
#else
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>		
#define KAGUYA_SET_CON_TYPE_DEF(N) typename nativefunction::functionToConstructorSignature<class_type,KAGUYA_PP_CAT(A,N)>::type()
#define KAGUYA_SET_CON_FN_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
	inline UserdataMetatable& setConstructors()\
	{\
		member_map_["new"] = metatable_detail::makeDataHolder(overload(KAGUYA_PP_REPEAT_ARG(N,KAGUYA_SET_CON_TYPE_DEF)));\
		return *this;\
	}
			KAGUYA_PP_REPEAT_DEF(9, KAGUYA_SET_CON_FN_DEF)
#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_SET_CON_FN_DEF
#undef KAGUYA_SET_CON_TYPE_DEF

#endif


		//add member property
		template<typename Ret>
		UserdataMetatable& addProperty(const char* name, Ret class_type::* mem)
		{
			if (has_key(name))
			{
				//already registerd
				return *this;
			}
			property_map_[name] = metatable_detail::makeDataHolder(function(mem));
			return *this;
		}

		template<typename Fun>
		UserdataMetatable& addStaticFunction(const char* name, Fun f)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd.");
				return *this;
			}
			member_map_[name] = metatable_detail::makeDataHolder(function(f));
			return *this;
		}

		//add field to 
		UserdataMetatable& addCodeChunkResult(const char* name, const std::string& lua_code_chunk)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd.");
				return *this;
			}
			code_chunk_map_[name] = lua_code_chunk;
			return *this;
		}



		template<typename Data>
		UserdataMetatable& addStaticField(const char* name, const Data& d)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd.");
				return *this;
			}
			member_map_[name] = metatable_detail::makeDataHolder(d);
			return *this;
		}
#if KAGUYA_USE_CPP11
		template<typename... Funcs>
		UserdataMetatable& addOverloadedFunctions(const char* name, Funcs... f)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd."); 
				return *this;
			}

			member_map_[name] = metatable_detail::makeDataHolder(overload(f...));

			return *this;
		}
		template<typename Data>
		UserdataMetatable& addStaticField(const char* name, Data&& d)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd.");
				return *this;
			}
			member_map_[name] = metatable_detail::makeDataHolder(std::forward<Data>(d));
			return *this;
		}
#else

#define KAGUYA_PP_TEMPLATE(N) KAGUYA_PP_CAT(typename A,N)
#define KAGUYA_PP_FARG(N) const KAGUYA_PP_CAT(A,N)& KAGUYA_PP_CAT(a,N)
#define KAGUYA_PP_FUNCS(N) KAGUYA_PP_CAT(a,N)
#define KAGUYA_ADD_OVERLOAD_FUNCTION_DEF(N) template<KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_TEMPLATE)>\
		inline UserdataMetatable& addOverloadedFunctions(const char* name,KAGUYA_PP_REPEAT_ARG(N,KAGUYA_PP_FARG))\
		{\
			if (has_key(name))\
			{\
				throw KaguyaException("already registerd.");\
				return *this;\
			}\
			member_map_[name] = metatable_detail::makeDataHolder(overload(KAGUYA_PP_ARG_REPEAT(N)));\
			return *this;\
		}
		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_ADD_OVERLOAD_FUNCTION_DEF)
#undef KAGUYA_PP_TEMPLATE
#undef KAGUYA_PP_FARG
#undef KAGUYA_PP_FUNCS
#undef KAGUYA_ADD_OVERLOAD_FUNCTION_DEF
#endif

#if defined(_MSC_VER) && _MSC_VER <= 1800
		//can not write  Ret class_type::* f on MSC++2013
		template<typename Fun>
		UserdataMetatable& addFunction(const char* name, Fun f)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd. if you want function overload,use addOverloadedFunctions");
				return *this;
			}
			member_map_[name] = metatable_detail::makeDataHolder(function(f));
			return *this;
		}
#else
		template<typename Ret>
		UserdataMetatable& addFunction(const char* name, Ret class_type::* f)
		{
			if (has_key(name))
			{
				throw KaguyaException("already registerd. if you want function overload,use addOverloadedFunctions");
				return *this;
			}
			member_map_[name] = metatable_detail::makeDataHolder(function(f));
			return *this;
		}
#endif
		

#if defined(_MSC_VER) && _MSC_VER <= 1800
		//can not use add at MSVC2013
#else
		template<typename Data>
		KAGUYA_DEPRECATED_FEATURE("add is deprecated. use addStaticField instead.")
			UserdataMetatable& add(const char* name, const Data& d)
		{
			return addStaticField(name, d);
		}

#if KAGUYA_USE_CPP11
		template<typename Data>
		KAGUYA_DEPRECATED_FEATURE("add is deprecated. use addStaticField instead.")
			UserdataMetatable& add(const char* name, Data&& d)
		{
			return addStaticField(name, d);
		}
#endif
		template<typename Ret>
		KAGUYA_DEPRECATED_FEATURE("add is deprecated. use addFunction instead.")
		UserdataMetatable& add(const char* name, Ret class_type::* f)
		{
			return addFunction(name,f);
		}
#endif

	private:

		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<void>)const
		{
		}
		template<class Base>
		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<Base>)const
		{
			class_userdata::get_metatable<Base>(state);
			metatable.setMetatable(LuaTable(state, StackTop()));

			PointerConverter& pconverter = PointerConverter::get(state);
			pconverter.add_type_conversion<Base, class_type>();
		}

		void set_multiple_base(lua_State* state, LuaTable& metatable, const LuaTable& metabases)const
		{
			LuaTable newmeta(state, NewTable());

			LuaFunction indexfun = kaguya::LuaFunction::loadstring(state, "local arg = {...};local metabases = arg[1];"
				"return function(t, k)"
				" for i = 1,#metabases do "
				" local v = metabases[i][k] "
				" if v then "
				" t[k] = v "
				" return v end "
				" end"
				" end")(metabases);

			newmeta.setField("__index", indexfun);

			metatable.setMetatable(newmeta);

		}
#if KAGUYA_USE_CPP11

		template<typename Base>
		void metatables(lua_State* state, LuaTable& metabases, PointerConverter& pvtreg, types::typetag<MultipleBase<Base> >)const
		{
			class_userdata::get_metatable<Base>(state);
			metabases.setField(metabases.size() + 1, LuaTable(state, StackTop()));
			pvtreg.add_type_conversion<Base, class_type>();
		}
		template<typename Base, typename... Remain>
		void metatables(lua_State* state, LuaTable& metabases, PointerConverter& pvtreg, types::typetag<MultipleBase<Base, Remain...> >)const
		{
			class_userdata::get_metatable<Base>(state);
			metabases.setField(metabases.size() + 1, LuaTable(state, StackTop()));
			pvtreg.add_type_conversion<Base, class_type>();
			metatables(state, metabases, pvtreg, types::typetag<MultipleBase<Remain...> >());
		}

		template<typename... Bases>
		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<MultipleBase<Bases...> > metatypes)const
		{
			PointerConverter& pconverter = PointerConverter::get(state);
			LuaTable metabases(state, NewTable(sizeof...(Bases), 0));
			metatables(state, metabases, pconverter, metatypes);
			set_multiple_base(state, metatable, metabases);
		}

#else
#define KAGUYA_TEMPLATE_PARAMETER(N) template<KAGUYA_PP_TEMPLATE_DEF_REPEAT(N)>
#define KAGUYA_GET_BASE_METATABLE(N) class_userdata::get_metatable<KAGUYA_PP_CAT(A,N)>(state);\
		metabases.setField(metabases.size() + 1, LuaTable(state, StackTop())); \
		pconverter.add_type_conversion<KAGUYA_PP_CAT(A,N),class_type>();
#define KAGUYA_MULTIPLE_INHERITANCE_SETBASE_DEF(N) \
	KAGUYA_TEMPLATE_PARAMETER(N)\
		void set_base_metatable(lua_State* state, LuaTable& metatable, types::typetag<MultipleBase<KAGUYA_PP_TEMPLATE_ARG_REPEAT(N)> > metatypes)const\
		{\
			PointerConverter& pconverter = PointerConverter::get(state);\
			LuaTable metabases(state, NewTable(N, 0));\
			KAGUYA_PP_REPEAT(N, KAGUYA_GET_BASE_METATABLE); \
			set_multiple_base(state, metatable, metabases);\
		}\

		KAGUYA_PP_REPEAT_DEF(9, KAGUYA_MULTIPLE_INHERITANCE_SETBASE_DEF)
#undef KAGUYA_TEMPLATE_PARAMETER
#undef KAGUYA_MULTIPLE_INHERITANCE_SETBASE_DEF
#undef KAGUYA_GET_BASE_METATABLE
#undef KAGUYA_TYPE_CHECK_REP
#endif

		bool has_key(const std::string& key, bool exclude_function = false)
		{
			if (member_map_.find(key) != member_map_.end())
			{
				return true;
			}
			if (property_map_.find(key) != property_map_.end())
			{
				return true;
			}
			return false;
		}
		void registerField(lua_State* state, const char* name, const metatable_detail::DataHolderType& value)const
		{
			int count = value->push_to_lua(state);
			if (count > 1)
			{
				lua_pop(state, count - 1);
				count = 1;
			}
			if (count == 1)
			{
				lua_setfield(state, -2, name);
			}
		}
		void registerCodeChunk(lua_State* state, const char* name,const std::string& value)const
		{
			util::ScopedSavedStack save(state);
			int status = luaL_loadstring(state, value.c_str());
			if (!except::checkErrorAndThrow(status, state)) { return; }
			status = lua_pcall_wrap(state, 0, 1);
			if (!except::checkErrorAndThrow(status, state)) { return; }
			lua_setfield(state, -2, name);
		}

		void registerMember(lua_State* state)const
		{
			for (typename MemberMapType::const_iterator it = member_map_.begin(); it != member_map_.end(); ++it)
			{
				registerField(state, it->first.c_str(), it->second);
			}
			for (typename CodeChunkMapType::const_iterator it = code_chunk_map_.begin(); it != code_chunk_map_.end(); ++it)
			{
				registerCodeChunk(state, it->first.c_str(), it->second);
			}
		}

		PropMapType property_map_;
		MemberMapType member_map_;
		CodeChunkMapType code_chunk_map_;
	};
}

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)


#include <vector>
#include <map>
#include <cassert>


namespace kaguya
{
	class State;


	struct table_proxy
	{

#if KAGUYA_USE_CPP11
		template<typename V, typename KEY>
		static void set(lua_State* state, int table_index, const KEY& key, V&& value)
		{
			util::one_push(state, key);
			util::one_push(state, std::forward<V>(value));
			lua_settable(state, table_index);
		}
		template<typename V>
		static void set(lua_State* state, int table_index, const char* key, V&& value)
		{
			util::one_push(state, std::forward<V>(value));
			lua_setfield(state, table_index, key);
		}
		template<typename V>
		static void set(lua_State* state, int table_index, const std::string& key, V&& value)
		{
			util::one_push(state, std::forward<V>(value));
			lua_setfield(state, table_index, key.c_str());
		}

#endif
		template<typename V, typename KEY>
		static void set(lua_State* state, int table_index, const KEY& key, const V& value)
		{
			util::one_push(state, key);
			util::one_push(state, value);
			lua_settable(state, table_index);
		}
		template<typename V>
		static void set(lua_State* state, int table_index, const char* key, const V& value)
		{
			util::one_push(state, value);
			lua_setfield(state, table_index, key);
		}
		template<typename V>
		static void set(lua_State* state, int table_index, const std::string& key, const V& value)
		{
			util::one_push(state, value);
			lua_setfield(state, table_index, key.c_str());
		}

		template<typename KEY>
		static void get(lua_State* state, int table_index, const KEY& key)
		{
			util::one_push(state, key);
			lua_gettable(state, table_index);
		}
		static void get(lua_State* state, int table_index, const char* key)
		{
			lua_getfield(state, table_index, key);
		}
		static void get(lua_State* state, int table_index, const std::string& key)
		{
			lua_getfield(state, table_index, key.c_str());
		}
	};


	/**
	* This class is the type returned by members of non-const LuaRef(Table) when directly accessing its elements.
	*/
	template<typename KEY>
	class TableKeyReference : public LuaVariantImpl<TableKeyReference<KEY> >
	{
	public:

		int pushStackIndex(lua_State* state)const
		{
			push(state);
			return lua_gettop(state);
		}
		lua_State* state()const { return state_; }

		friend class LuaRef;
		friend class State;
		
		//! this is not copy.same assign from referenced value.
		TableKeyReference& operator=(const TableKeyReference& src)
		{
			table_proxy::set(state_, table_index_, key_, src);
			return *this;
		}


		//! assign from T
		template<typename T>
		TableKeyReference& operator=(const T& src)
		{
			table_proxy::set(state_, table_index_, key_, src);

			return *this;
		}
#if KAGUYA_USE_CPP11
		template<typename T>
		TableKeyReference& operator=(T&& src)
		{
			table_proxy::set(state_, table_index_, key_, std::forward<T>(src));
			return *this;
		}
#endif

		bool isNilref()const {
			if (!state_)
			{
				return false;
			}
			util::ScopedSavedStack save(state_);
			push(state_);
			return lua_isnoneornil(state_, -1);
		}

		//! register class metatable to lua and set to table
		template<typename T, typename P>
		void setClass(const ClassMetatable<T, P>& reg)
		{
			set_class(reg);
		}

		//! register class metatable to lua and set to table
		template<typename T, typename P>
		void setClass(const UserdataMetatable<T, P>& reg)
		{
			set_class(reg);
		}

		//! set function 
		template<typename T>
		void setFunction(T f)
		{
			table_proxy::set(state_, table_index_, key_, FunctorType(f));
		}

		//deprecated
		LuaRef getValue()const
		{
			util::ScopedSavedStack save(state_);
			push(state_);
			return lua_type_traits<LuaRef>::get(state_, -1);
		}


		int push(lua_State* state)const
		{
			if (lua_type(state_, table_index_) != LUA_TTABLE)
			{
				lua_pushnil(state);
				return 1;
			}

			table_proxy::get(state_, table_index_, key_);

			if (state_ != state)
			{
				lua_xmove(state_, state, 1);
			}
			return 1;

		}

		int push()const
		{
			return push(state_);
		}


		/**
		* @brief table->*"function_name"() in c++ and table:function_name(); in lua is same
		* @param function_name function_name in table
		*/
//		mem_fun_binder operator->*(const char* function_name)
//		{
//			return get<LuaRef>()->*(function_name);
//		}


		int type()const
		{
			util::ScopedSavedStack save(state_);
			push();
			return lua_type(state_, -1);
		}

		~TableKeyReference()
		{
			if (state_)
			{
				lua_settop(state_, stack_top_);
			}
		}

		///!constructs the reference. Accessible only to kaguya::LuaRef itself 
		TableKeyReference(const TableKeyReference& src) : state_(src.state_), stack_top_(src.stack_top_), table_index_(src.table_index_), key_(src.key_)
		{
			src.state_ = 0;
		}

		///!constructs the reference. Accessible only to kaguya::LuaRef itself 
		TableKeyReference(lua_State* state, int table_index, KEY key, int revstacktop) : state_(state), stack_top_(revstacktop), table_index_(table_index), key_(key)
		{
		}
	private:
		template<typename T, typename P>
		void set_class(const ClassMetatable<T, P>& reg)
		{
			LuaRef table(state_, NewTable());
			table.setMetatable(reg.registerClass(state_));
			*this = table;
		}

		template<typename T, typename P>
		void set_class(const UserdataMetatable<T, P>& reg)
		{
			LuaRef table(state_, NewTable());
			table.setMetatable(reg.registerClass(state_));
			*this = table;
		}


		///!constructs the reference. Accessible only to kaguya::LuaRef itself 
		TableKeyReference(lua_State* state, int table_index, const KEY& key, int revstacktop, const NoTypeCheck&) : state_(state), stack_top_(revstacktop), table_index_(table_index), key_(key)
		{
		}

		TableKeyReference(const LuaTable& table, const KEY& key) : state_(table.state()), stack_top_(lua_gettop(state_)), key_(key)
		{
			lua_type_traits<LuaRef>::push(state_, table);
			lua_type_traits<KEY>::push(state_, key);
			table_index_ = stack_top_ + 1;
		}
		TableKeyReference(const LuaRef& table, const KEY& key) : state_(table.state()), stack_top_(lua_gettop(state_)), key_(key)
		{
			lua_type_traits<LuaRef>::push(state_, table);
			lua_type_traits<KEY>::push(state_, key);
			table_index_ = stack_top_ + 1;
			if (lua_type(state_, table_index_) != LUA_TTABLE)
			{
				except::typeMismatchError(state_, "this is not table");
			}
		}

		mutable lua_State* state_;//mutable for RVO unsupported compiler
		int stack_top_;
		int table_index_;
		KEY key_;
	};

	template<typename KEY>
	inline std::ostream& operator<<(std::ostream& os, const TableKeyReference<KEY>& ref)
	{
		lua_State* state = ref.state();
		util::ScopedSavedStack save(state);
		int stackIndex = ref.pushStackIndex(state);
		util::stackValueDump(os, state, stackIndex);
		return os;
	}

	template<typename T>
	inline bool LuaFunctionImpl<T>::setFunctionEnv(const LuaTable& env)
	{
		util::ScopedSavedStack save(state_());
		int stackIndex = pushStackIndex_(state_());
		env.push();
#if LUA_VERSION_NUM >= 502
		lua_setupvalue(state_(), stackIndex, 1);
#else
		lua_setfenv(state_(), stackIndex);
#endif
		return true;
	}
	template<typename T>
	inline bool LuaFunctionImpl<T>::setFunctionEnv(NewTable env)
	{
		return setFunctionEnv(LuaTable(state_()));
	}

	template<typename T>
	inline LuaTable LuaFunctionImpl<T>::getFunctionEnv()
	{
		util::ScopedSavedStack save(state_());
		int stackIndex = pushStackIndex_(state_());

#if LUA_VERSION_NUM >= 502
		lua_getupvalue(state_(), stackIndex, 1);
#else
		lua_getfenv(state_(), stackIndex);
#endif
		return LuaTable(state_(), StackTop());
	}

	template<typename T>
	bool LuaTableOrUserDataImpl<T>::setMetatable(const LuaTable& table)
	{
		lua_State* state = state_();
		if (!state)
		{
			except::typeMismatchError(state, "is nil");
			return false;
		}
		util::ScopedSavedStack save(state);
		int stackindex = pushStackIndex_(state);
		int t = lua_type(state, stackindex);
		if (t != LUA_TTABLE && t != LUA_TUSERDATA)
		{
			except::typeMismatchError(state, lua_typename(state, t) + std::string("is not table"));
			return false;
		}
		table.push();
		return lua_setmetatable(state, stackindex) != 0;
	}
	template<typename T>
	LuaTable LuaTableOrUserDataImpl<T>::getMetatable()const
	{
		lua_State* state = state_();
		if (!state)
		{
			except::typeMismatchError(state, "is nil");
			return LuaRef(state);
		}
		util::ScopedSavedStack save(state);
		int stackindex = pushStackIndex_(state);
		int t = lua_type(state, stackindex);
		if (t != LUA_TTABLE && t != LUA_TUSERDATA)
		{
			except::typeMismatchError(state, lua_typename(state, t) + std::string("is not table"));
			return LuaRef(state);
		}
		lua_getmetatable(state, stackindex);
		return LuaTable(state, StackTop());
	}
	template<typename T>
	mem_fun_binder LuaTableOrUserDataImpl<T>::operator->*(const char* function_name)
	{
		push_(state_());
		return mem_fun_binder(LuaRef(state_(), StackTop()), function_name);
	}


	template<typename T> template <typename KEY>
	LuaStackRef LuaTableOrUserDataImpl<T>::getField(const KEY& key)const
	{
		lua_State* state = state_();
		if (!state)
		{
			except::typeMismatchError(state, "is nil");
			return LuaStackRef();
		}
		if (push_(state))//push table
		{
			lua_type_traits<KEY>::push(state, key);//push key
			lua_gettable(state, -2);//get table[key]
			lua_remove(state, -3);//remove table
			return LuaStackRef(state, -1,true);
		}
		return LuaStackRef();
	}

	template<typename T> template<typename KEY>
	LuaStackRef LuaTableOrUserDataImpl<T>::operator[](KEY key)const
	{
		return getField(key);
	}

	template<typename T>
	std::vector<LuaRef> LuaTableOrUserDataImpl<T>::values()const
	{
		return values<LuaRef>();
	}
	template<typename T>
	std::vector<LuaRef> LuaTableOrUserDataImpl<T>::keys()const
	{
		return keys<LuaRef>();
	}
	template<typename T>
	std::map<LuaRef, LuaRef> LuaTableOrUserDataImpl<T>::map()const
	{
		return map<LuaRef, LuaRef>();
	}

	template<typename T> template <typename K>
	TableKeyReference<K> LuaTableOrUserDataImpl<T>::operator[](K key)
	{
		lua_State* state = state_();
		int stack_top = lua_gettop(state);
		int stackindex = pushStackIndex_(state);
		return TableKeyReference<K>(state, stackindex, key, stack_top);
	}


	template<typename KEY>
	struct lua_type_traits<TableKeyReference<KEY> > {
		static int push(lua_State* l, const TableKeyReference<KEY>& ref)
		{
			ref.push(l);
			return 1;
		}
	};

#ifndef KAGUYA_NO_STD_VECTOR_TO_TABLE
	template<typename T, typename A>
	struct lua_type_traits<std::vector<T, A> >
	{
		typedef std::vector<T, A> get_type;
		typedef const std::vector<T, A>& push_type;

		struct checkTypeForEach
		{
			checkTypeForEach(bool& valid) :valid_(valid) {}
			bool& valid_;
			bool operator()(const LuaStackRef& k, const LuaStackRef& v)
			{
				valid_ = k.typeTest<size_t>() && v.weakTypeTest<T>();
				return valid_;
			}
		};
		struct strictCheckTypeForEach
		{
			strictCheckTypeForEach(bool& valid) :valid_(valid) {}
			bool& valid_;
			bool operator()(const LuaStackRef& k, const LuaStackRef& v)
			{
				valid_ = k.typeTest<size_t>() && v.typeTest<T>();
				return valid_;
			}
		};

		static bool checkType(lua_State* l, int index)
		{
			LuaStackRef table(l, index);
			if (table.type() != LuaRef::TYPE_TABLE) { return false; }
			
			bool valid = true;
			table.foreach_table_breakable<LuaStackRef, LuaStackRef>(checkTypeForEach(valid));
			return valid;
		}
		static bool strictCheckType(lua_State* l, int index)
		{
			LuaStackRef table(l, index);
			if (table.type() != LuaRef::TYPE_TABLE) { return false; }

			bool valid = true;
			table.foreach_table_breakable<LuaStackRef, LuaStackRef>(strictCheckTypeForEach(valid));
			return valid;
		}

		static get_type get(lua_State* l, int index)
		{
			if (lua_type(l, index) != LUA_TTABLE)
			{
				except::typeMismatchError(l, std::string("type mismatch"));
				return get_type();
			}
			return LuaStackRef(l, index).values<T>();
		}
		static int push(lua_State* l, push_type v)
		{
			LuaRef table(l, NewTable(int(v.size()), 0));

			int count = 1;//array is 1 origin in Lua
			for (typename std::vector<T>::const_iterator it = v.begin(); it != v.end(); ++it)
			{
				table.setField(count++, *it);
			}
			table.push(l);
			return 1;
		}
	};
#endif

#ifndef KAGUYA_NO_STD_MAP_TO_TABLE
	template<typename K, typename V, typename C, typename A>
	struct lua_type_traits<std::map<K, V, C, A> >
	{
		typedef std::map<K, V, C, A> get_type;
		typedef const std::map<K, V, C, A>& push_type;

		struct checkTypeForEach
		{
			checkTypeForEach(bool& valid) :valid_(valid) {}
			bool& valid_;
			bool operator()(const LuaStackRef& k, const LuaStackRef& v)
			{
				valid_ = k.weakTypeTest<K>() && v.weakTypeTest<V>();
				return valid_;
			}
		};
		struct strictCheckTypeForEach
		{
			strictCheckTypeForEach(bool& valid) :valid_(valid) {}
			bool& valid_;
			bool operator()(const LuaStackRef& k, const LuaStackRef& v)
			{
				valid_ = k.typeTest<K>() && v.typeTest<V>();
				return valid_;
			}
		};
		static bool checkType(lua_State* l, int index)
		{
			LuaStackRef table(l, index);
			if (table.type() != LuaRef::TYPE_TABLE) { return false; }

			bool valid = true;
			table.foreach_table_breakable<LuaStackRef, LuaStackRef>(checkTypeForEach(valid));
			return valid;
		}
		static bool strictCheckType(lua_State* l, int index)
		{
			LuaStackRef table(l, index);
			if (table.type() != LuaRef::TYPE_TABLE) { return false; }
			
			bool valid = true;
			table.foreach_table_breakable<LuaStackRef, LuaStackRef>(strictCheckTypeForEach(valid));
			return valid;
		}

		static get_type get(lua_State* l, int index)
		{
			if (lua_type(l, index) != LUA_TTABLE)
			{
				except::typeMismatchError(l, std::string("type mismatch"));
				return get_type();
			}
			return LuaStackRef(l, index).map<K, V>();
		}
		static int push(lua_State* l, push_type v)
		{
			LuaRef table(l, NewTable(0, int(v.size())));
			for (typename std::map<K, V>::const_iterator it = v.begin(); it != v.end(); ++it)
			{
				table.setField(it->first, it->second);
			}
			table.push(l);
			return 1;
		}
	};
#endif
}

namespace kaguya
{
	typedef std::pair<std::string, lua_CFunction> LoadLib;
	typedef std::vector<LoadLib> LoadLibs;
	inline LoadLibs NoLoadLib() { return LoadLibs(); }

	template<typename Allocator>
	void * AllocatorFunction(void *ud,
		void *ptr,
		size_t osize,
		size_t nsize)
	{
		Allocator* allocator = static_cast<Allocator*>(ud);
		if (!allocator)
		{
			return std::realloc(ptr, nsize);
		}
		if (nsize == 0)
		{
			allocator->deallocate(ptr, osize);
		}
		else if (ptr)
		{
			return allocator->reallocate(ptr, nsize);
		}
		else
		{
			return allocator->allocate(nsize);
		}
		return 0;
	}

	struct DefaultAllocator
	{
		typedef void* pointer;
		typedef size_t size_type;
		pointer allocate(size_type n)
		{
			return std::malloc(n);
		}
		pointer reallocate(pointer p, size_type n)
		{
			return std::realloc(p,n);
		}
		void deallocate(pointer p, size_type n)
		{
			std::free(p);
		}
	};

	class State
	{
		standard::shared_ptr<void> allocator_holder_;
		lua_State *state_;
		bool created_;

		//non copyable
		State(const State&);
		State& operator =(const State&);


		static int default_panic(lua_State *L) {
			fprintf(stderr, "PANIC: unprotected error in call to Lua API (%s)\n", lua_tostring(L, -1));
			fflush(stderr);
			return 0;  /* return to Lua to abort */
		}
		static void stderror_out(int status, const char* message)
		{
			std::cerr << message << std::endl;
		}
		void init()
		{
			if (!ErrorHandler::getHandler(state_))
			{
				setErrorHandler(&stderror_out);
			}
			nativefunction::reg_functor_destructor(state_);
		}

	public:

		//! 

		/**
		* @name constructor
		* @brief create Lua state with lua standard library
		*/
		State() :allocator_holder_(), state_(luaL_newstate()), created_(true)
		{
			lua_atpanic(state_, &default_panic);
			init();
			openlibs();
		}

		/**
		* @name constructor
		* @brief create Lua state with lua standard library. Can not use this constructor at luajit. error message is 'Must use luaL_newstate() for 64 bit target'
		* @param allocator allocator for memory allocation @see DefaultAllocator
		*/
		template<typename Allocator>
		State(standard::shared_ptr<Allocator> allocator) :allocator_holder_(allocator), state_(lua_newstate(&AllocatorFunction<Allocator>, allocator_holder_.get())), created_(true)
		{
			lua_atpanic(state_, &default_panic);
			init();
			openlibs();
		}

		/**
		* @name constructor
		* @brief create Lua state with (or without) libraries.
		* @param libs load libraries 
		* e.g. LoadLibs libs;libs.push_back(LoadLib("libname",libfunction));State state(libs);
		* e.g. State state({{"libname",libfunction}}); for c++ 11
		*/
		State(const LoadLibs& libs) : allocator_holder_(), state_(luaL_newstate()), created_(true)
		{
			lua_atpanic(state_, &default_panic);
			init();
			openlibs(libs);
		}
		/**
		* @name constructor
		* @brief create Lua state with (or without) libraries. Can not use this constructor at luajit. error message is 'Must use luaL_newstate() for 64 bit target'
		* @param allocator allocator for memory allocation @see DefaultAllocator
		*/
		template<typename Allocator>
		State(const LoadLibs& libs, standard::shared_ptr<Allocator> allocator) : allocator_holder_(allocator), state_(lua_newstate(&AllocatorFunction<Allocator>, allocator_holder_.get())), created_(true)
		{
			lua_atpanic(state_, &default_panic);
			init();
			openlibs(libs);
		}


		/**
		* @name constructor
		* @brief construct using created lua_State. 
		* @param lua created lua_State. It is not call lua_close() in this class
		*/
		State(lua_State* lua) :state_(lua), created_(false)
		{
			init();
		}
		~State()
		{
			if (created_)
			{
				lua_close(state_);
			}
		}

		/**
		* @name setErrorHandler
		* @brief set error handler for lua error.
		*/
		void setErrorHandler(standard::function<void(int statuscode, const char*message)> errorfunction)
		{
			util::ScopedSavedStack save(state_);
			ErrorHandler::registerHandler(state_, errorfunction);
		}


		/**
		* @name openlibs
		* @brief load all lua standard library
		*/
		void openlibs()
		{
			util::ScopedSavedStack save(state_);
			luaL_openlibs(state_);
		}

		/**
		* @name openlib
		* @brief load lua library
		*/
		void openlib(const LoadLib& lib)
		{
			util::ScopedSavedStack save(state_);

			luaL_requiref(state_, lib.first.c_str(), lib.second, 1);
		}
		
		/**
		* @name openlibs
		* @brief load lua libraries
		*/
		void openlibs(const LoadLibs& libs)
		{
			for (LoadLibs::const_iterator it = libs.begin(); it != libs.end(); ++it)
			{
				openlib(*it);
			}
		}

		/**
		* @name loadfile
		* @brief If there are no errors,compiled file as a Lua function and return.
		*  Otherwise send error message to error handler and return nil reference
		* @param file  file path of lua script
		* @return reference of lua function
		*/
		//@{
		LuaFunction loadfile(const std::string& file)
		{
			return LuaFunction::loadfile(state_, file);
		}
		LuaFunction loadfile(const char* file)
		{
			return LuaFunction::loadfile(state_, file);
		}
		//@}


		/**
		* @name loadstream
		* @brief If there are no errors,compiled stream as a Lua function and return.
		*  Otherwise send error message to error handler and return nil reference
		* @param stream stream of lua script
		* @return reference of lua function
		*/
		//@{
		LuaFunction loadstream(std::istream& stream,const std::string& chunkname="")
		{
			return loadstream(stream, chunkname.c_str());
		}
		LuaFunction loadstream(std::istream& stream, const char* chunkname="")
		{
			return LuaFunction::loadstream(state_, stream, chunkname);
		}
		//@}
		/**
		* @name dostream
		* @brief Loads and runs the given stream.
		* @param stream stream of lua script
		* @param env execute env table
		* @return If there are no errors, returns true.Otherwise return false
		*/
		//@{
		bool dostream(std::istream& stream, const std::string& chunkname = "", const LuaTable& env = LuaTable())
		{
			return dostream(stream, chunkname.c_str(), env);
		}
		bool dostream(std::istream& stream, const char* chunkname = "", const LuaTable& env = LuaTable())
		{
			LuaFunction f = loadstream(stream,chunkname);
			if (!env.isNilref())
			{
				f.setFunctionEnv(env);
			}

			FunctionResults ret= f.call<FunctionResults>();
			return !ret.resultStatus();
		}
		//@}


		/**
		* @name loadstring
		* @brief If there are no errors,compiled string as a Lua function and return.
		*  Otherwise send error message to error handler and return nil reference
		* @param str lua code
		* @return reference of lua function
		*/
		//@{
		LuaFunction loadstring(const std::string& str)
		{
			return LuaFunction::loadstring(state_, str);
		}
		LuaFunction loadstring(const char* str)
		{
			return LuaFunction::loadstring(state_, str);
		}
		//@}

		/**
		* @name dofile
		* @brief Loads and runs the given file.
		* @param file file path of lua script
		* @param env execute env table
		* @return If there are no errors, returns true.Otherwise return false
		*/
		//@{
		bool dofile(const std::string& file, const LuaTable& env = LuaTable())
		{
			return dofile(file.c_str(), env);
		}
		bool dofile(const char* file, const LuaTable& env = LuaTable())
		{
			util::ScopedSavedStack save(state_);

			int status = luaL_loadfile(state_, file);

			if (status)
			{
				ErrorHandler::handle(status, state_);
				return false;
			}

			if (!env.isNilref())
			{//register _ENV
				env.push();
#if LUA_VERSION_NUM >= 502
				lua_setupvalue(state_, -2, 1);
#else
				lua_setfenv(state_, -2);
#endif
			}

			status = lua_pcall_wrap(state_, 0, LUA_MULTRET);
			if (status)
			{
				ErrorHandler::handle(status, state_);
				return false;
			}
			return true;
		}
		//@}

		/**
		* @name dostring
		* @brief Loads and runs the given string.
		* @param str lua script cpde
		* @param env execute env table
		* @return If there are no errors, returns true.Otherwise return false
		*/
		bool dostring(const char* str, const LuaTable& env = LuaTable())
		{
			util::ScopedSavedStack save(state_);

			int status = luaL_loadstring(state_, str);
			if (status)
			{
				ErrorHandler::handle(status, state_);
				return false;
			}
			if (!env.isNilref())
			{//register _ENV
				env.push();
#if LUA_VERSION_NUM >= 502
				lua_setupvalue(state_, -2, 1);
#else
				lua_setfenv(state_, -2);
#endif
			}
			status = lua_pcall_wrap(state_, 0, LUA_MULTRET);
			if (status)
			{
				ErrorHandler::handle(status, state_);
				return false;
			}
			return true;
		}
		bool dostring(const std::string& str, const LuaTable& env = LuaTable())
		{
			return dostring(str.c_str(), env);
		}
		bool operator()(const std::string& str)
		{
			return dostring(str);
		}
		bool operator()(const char* str)
		{
			return dostring(str);
		}
		//@}

		//! return element reference from global table
		TableKeyReference<std::string> operator[](const std::string& str)
		{
			int stack_top = lua_gettop(state_);
			lua_type_traits<GlobalTable>::push(state_, GlobalTable());
			int table_index = stack_top + 1;
			return TableKeyReference<std::string>(state_, table_index, str, stack_top, NoTypeCheck());
		}

		//! return element reference from global table
		TableKeyReference<const char*> operator[](const char* str)
		{
			int stack_top = lua_gettop(state_);
			lua_type_traits<GlobalTable>::push(state_, GlobalTable());
			int table_index = stack_top + 1;
			return TableKeyReference<const char*>(state_, table_index, str, stack_top, NoTypeCheck());
		}

		//! return global table
		LuaTable globalTable()
		{
			return newRef(GlobalTable());
		}

		//! return new Lua reference from argument value
		template<typename T>
		LuaRef newRef(const T& value)
		{
			return LuaRef(state_, value);
		}
#if KAGUYA_USE_CPP11
		//! return new Lua reference from argument value
		template<typename T>
		LuaRef newRef(T&& value)
		{
			return LuaRef(state_, std::forward<T>(value));
		}
#endif

		//! return new Lua table
		LuaTable newTable()
		{
			return LuaTable(state_);
		}
		//! return new Lua table
		LuaTable newTable(int reserve_array, int reserve_record)
		{
			return LuaTable(state_, NewTable(reserve_array, reserve_record));
		}

		//! create new Lua thread
		LuaThread newThread()
		{
			return LuaThread(state_);
		}
		//! create new Lua thread with function
		LuaThread newThread(const LuaFunction& f)
		{
			LuaThread cor(state_);
			cor.setFunction(f);
			return cor;
		}

		//push to Lua stack
		template<typename T>
		void pushToStack(T value)
		{
			lua_type_traits<T>::push(state_, value);
		}
		LuaRef popFromStack()
		{
			return LuaRef(state_, StackTop());
		}

		//! Garbage Collection of Lua 
		struct GCType
		{
			GCType(lua_State* state) :state_(state) {}

			/**
			* Performs a full garbage-collection cycle.
			*/
			void collect()
			{
				lua_gc(state_, LUA_GCCOLLECT, 0);
			}
			/** Performs an incremental step of garbage collection.
			* @return If returns true,the step finished a collection cycle.
			*/
			bool step()
			{
				return lua_gc(state_, LUA_GCSTEP, 0) == 1;
			}
			/**
			* Performs an incremental step of garbage collection.
			* @args the collector will perform as if that amount of memory (in KBytes) had been allocated by Lua.
			*/
			bool step(int size)
			{
				return lua_gc(state_, LUA_GCSTEP, size) == 1;
			}
			/**
			* enable gc
			*/
			void restart() { enable(); }
			/**
			* disable gc
			*/
			void stop() { disable(); }

			/**
			* returns the total memory in use by Lua in Kbytes.
			*/
			int count()const { return lua_gc(state_, LUA_GCCOUNT, 0); }

			/**
			* sets arg as the new value for the pause of the collector. Returns the previous value for pause.
			*/
			int steppause(int value) { return lua_gc(state_, LUA_GCSETPAUSE, value); }
			/**
			*  sets arg as the new value for the step multiplier of the collector. Returns the previous value for step.
			*/
			int setstepmul(int value) { return lua_gc(state_, LUA_GCSETSTEPMUL, value); }

			/**
			* enable gc
			*/
			void enable()
			{
				lua_gc(state_, LUA_GCRESTART, 0);
			}

			/**
			* disable gc
			*/
			void disable()
			{
				lua_gc(state_, LUA_GCSTOP, 0);
			}
#if LUA_VERSION_NUM >= 502
			/**
			* returns a boolean that tells whether the collector is running
			*/
			bool isrunning()const { return isenabled(); }
			/**
			* returns a boolean that tells whether the collector is running
			*/
			bool isenabled()const
			{
				return lua_gc(state_, LUA_GCISRUNNING, 0) != 0;
			}
#endif

		private:
			lua_State* state_;
		};

		//! return Garbage collection interface.
		GCType gc()const
		{
			return GCType(state_);
		}
		//! performs a full garbage-collection cycle.
		void garbageCollect()
		{
			gc().collect();
		}

		//! returns the current amount of memory (in Kbytes) in use by Lua.
		size_t useKBytes()const
		{
			return size_t(gc().count());
		}



		/**
		* @brief create Table and push to stack.
		* using for Lua module
		* @return return Lua Table Reference
		*/
		LuaRef newLib()
		{
			LuaTable newtable = newTable();
			newtable.push(state_);
			return newtable;
		}

		/**
		* @brief return lua_State*.
		* @return lua_State*
		*/
		lua_State *state() { return state_; };
	};
}

// Copyright satoren
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)



namespace kaguya
{
//generated header by ref_tuple_generator.py
template<class T0>
struct ref_tuple1
{
  ref_tuple1(T0& va0):v0(va0){}
  T0& v0;
  static const int size=1;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
  }
};

template<class T0,class T1>
struct ref_tuple2
{
  ref_tuple2(T0& va0,T1& va1):v0(va0),v1(va1){}
  T0& v0;  T1& v1;
  static const int size=2;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
  }
};

template<class T0,class T1,class T2>
struct ref_tuple3
{
  ref_tuple3(T0& va0,T1& va1,T2& va2):v0(va0),v1(va1),v2(va2){}
  T0& v0;  T1& v1;  T2& v2;
  static const int size=3;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
  }
};

template<class T0,class T1,class T2,class T3>
struct ref_tuple4
{
  ref_tuple4(T0& va0,T1& va1,T2& va2,T3& va3):v0(va0),v1(va1),v2(va2),v3(va3){}
  T0& v0;  T1& v1;  T2& v2;  T3& v3;
  static const int size=4;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
    v3 =get<3>(fres);
  }
};

template<class T0,class T1,class T2,class T3,class T4>
struct ref_tuple5
{
  ref_tuple5(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4):v0(va0),v1(va1),v2(va2),v3(va3),v4(va4){}
  T0& v0;  T1& v1;  T2& v2;  T3& v3;  T4& v4;
  static const int size=5;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
    v3 =get<3>(fres);
    v4 =get<4>(fres);
  }
};

template<class T0,class T1,class T2,class T3,class T4,class T5>
struct ref_tuple6
{
  ref_tuple6(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5):v0(va0),v1(va1),v2(va2),v3(va3),v4(va4),v5(va5){}
  T0& v0;  T1& v1;  T2& v2;  T3& v3;  T4& v4;  T5& v5;
  static const int size=6;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
    v3 =get<3>(fres);
    v4 =get<4>(fres);
    v5 =get<5>(fres);
  }
};

template<class T0,class T1,class T2,class T3,class T4,class T5,class T6>
struct ref_tuple7
{
  ref_tuple7(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5,T6& va6):v0(va0),v1(va1),v2(va2),v3(va3),v4(va4),v5(va5),v6(va6){}
  T0& v0;  T1& v1;  T2& v2;  T3& v3;  T4& v4;  T5& v5;  T6& v6;
  static const int size=7;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
    v3 =get<3>(fres);
    v4 =get<4>(fres);
    v5 =get<5>(fres);
    v6 =get<6>(fres);
  }
};

template<class T0,class T1,class T2,class T3,class T4,class T5,class T6,class T7>
struct ref_tuple8
{
  ref_tuple8(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5,T6& va6,T7& va7):v0(va0),v1(va1),v2(va2),v3(va3),v4(va4),v5(va5),v6(va6),v7(va7){}
  T0& v0;  T1& v1;  T2& v2;  T3& v3;  T4& v4;  T5& v5;  T6& v6;  T7& v7;
  static const int size=8;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
    v3 =get<3>(fres);
    v4 =get<4>(fres);
    v5 =get<5>(fres);
    v6 =get<6>(fres);
    v7 =get<7>(fres);
  }
};

template<class T0,class T1,class T2,class T3,class T4,class T5,class T6,class T7,class T8>
struct ref_tuple9
{
  ref_tuple9(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5,T6& va6,T7& va7,T8& va8):v0(va0),v1(va1),v2(va2),v3(va3),v4(va4),v5(va5),v6(va6),v7(va7),v8(va8){}
  T0& v0;  T1& v1;  T2& v2;  T3& v3;  T4& v4;  T5& v5;  T6& v6;  T7& v7;  T8& v8;
  static const int size=9;
  template<class T>
  void operator=(const T& fres)
  {
    using standard::get;
    v0 =get<0>(fres);
    v1 =get<1>(fres);
    v2 =get<2>(fres);
    v3 =get<3>(fres);
    v4 =get<4>(fres);
    v5 =get<5>(fres);
    v6 =get<6>(fres);
    v7 =get<7>(fres);
    v8 =get<8>(fres);
  }
};

template<class T0>
ref_tuple1<T0> tie(T0& va0)
{
  return ref_tuple1<T0>(va0);
}

template<class T0,class T1>
ref_tuple2<T0,T1> tie(T0& va0,T1& va1)
{
  return ref_tuple2<T0,T1>(va0,va1);
}

template<class T0,class T1,class T2>
ref_tuple3<T0,T1,T2> tie(T0& va0,T1& va1,T2& va2)
{
  return ref_tuple3<T0,T1,T2>(va0,va1,va2);
}

template<class T0,class T1,class T2,class T3>
ref_tuple4<T0,T1,T2,T3> tie(T0& va0,T1& va1,T2& va2,T3& va3)
{
  return ref_tuple4<T0,T1,T2,T3>(va0,va1,va2,va3);
}

template<class T0,class T1,class T2,class T3,class T4>
ref_tuple5<T0,T1,T2,T3,T4> tie(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4)
{
  return ref_tuple5<T0,T1,T2,T3,T4>(va0,va1,va2,va3,va4);
}

template<class T0,class T1,class T2,class T3,class T4,class T5>
ref_tuple6<T0,T1,T2,T3,T4,T5> tie(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5)
{
  return ref_tuple6<T0,T1,T2,T3,T4,T5>(va0,va1,va2,va3,va4,va5);
}

template<class T0,class T1,class T2,class T3,class T4,class T5,class T6>
ref_tuple7<T0,T1,T2,T3,T4,T5,T6> tie(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5,T6& va6)
{
  return ref_tuple7<T0,T1,T2,T3,T4,T5,T6>(va0,va1,va2,va3,va4,va5,va6);
}

template<class T0,class T1,class T2,class T3,class T4,class T5,class T6,class T7>
ref_tuple8<T0,T1,T2,T3,T4,T5,T6,T7> tie(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5,T6& va6,T7& va7)
{
  return ref_tuple8<T0,T1,T2,T3,T4,T5,T6,T7>(va0,va1,va2,va3,va4,va5,va6,va7);
}

template<class T0,class T1,class T2,class T3,class T4,class T5,class T6,class T7,class T8>
ref_tuple9<T0,T1,T2,T3,T4,T5,T6,T7,T8> tie(T0& va0,T1& va1,T2& va2,T3& va3,T4& va4,T5& va5,T6& va6,T7& va7,T8& va8)
{
  return ref_tuple9<T0,T1,T2,T3,T4,T5,T6,T7,T8>(va0,va1,va2,va3,va4,va5,va6,va7,va8);
}

}

