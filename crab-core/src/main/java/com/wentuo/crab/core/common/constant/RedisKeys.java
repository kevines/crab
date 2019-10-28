package com.wentuo.crab.core.common.constant;

public  class RedisKeys {


    public static String repeatCommit = "user:cache:";

    ///用户相关缓存key
	public static class user{
		
		private static String userCachePrefix="gop:user:";
		///根据用户ID获取用户缓存KEY
		public static String getKey(String userId){
			return userCachePrefix+userId;
		}
	}


    ///订单缓存key
    public static class orders{
        private static String userCachePrefix="orders:cache:";
        ///根据用户ID获取用户缓存KEY
        public static String getKey(String tradeNo){
            return userCachePrefix+tradeNo;
        }
        public static String setKey(String tradeNo){
            return userCachePrefix+tradeNo;
        }
    }

    ///购物车缓存key
    public static class cart{
        private static String userCachePrefix="cart:cache:";
        ///根据用户ID获取用户缓存KEY
        public static String getKey(String userId){
            return userCachePrefix+userId;
        }
    }

    ///订单缓存key
    public static class business{
        private static String userCachePrefix="business:approved:";
        ///根据用户ID获取用户缓存KEY
        public static String getKey(String userId){
            return userCachePrefix+userId;
        }
    }


    /**
     * 快递相关常量
     */
    public  static  class  express{
	     private  static String experssNoPrefix="express:cache:no:";
	     public  static  String getExpressCacheKey(String expressNo){
	         return experssNoPrefix+expressNo;
         }
    }

    /**
     * 活动 合伙人商品
     */
    public  static  class  activity{
        private  static String experssNoPrefix="activity_partner_goods_";

        /**
         * 活动 合伙人商品
         * @return
         */
        public  static  String getActivityCacheKey(String userId,String goodsActivityBizId){
            return experssNoPrefix+userId+"_"+goodsActivityBizId;
        }
    }
}
