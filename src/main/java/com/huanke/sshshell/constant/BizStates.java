package com.huanke.sshshell.constant;

public class BizStates {

    public static final class SESSION_ATTRS {
        public static final String USER_ID = "userId";
        public static final String USER_TYPE = "userType";
        public static final String BASE_USER_INFO = "baseUserInfo";
        public static final String USER_INFO = "userInfo";
        public static final String USER_AUTH = "userAuth";
        public static final String UNIFORM_USER = "uniformUser";
    }

    public static final String DEFAULT_APP_SECRET = "gkyz000000000";
    
    public static final class IS_PLAN_STATE {
        public static final byte NO = 0;
        public static final byte VALID = 1;
        public static final byte OUTOFDATE = 2;
    }
    
    public static final class IS_EXPERT_STATE {
        public static final byte NO = 0;
        public static final byte YES = 1;
    }
    
    public static final class IS_BODY_TEST_STATE {
        public static final byte NO = 0;
        public static final byte YES = 1;
    }
    
    public static final class IS_HABBIT_TEST_STATE {
        public static final byte NO = 0;
        public static final byte YES = 1;
    }

    public static final class IS_PATIENT_BASE_INFO_STATE {
        public static final byte NO = 0;
        public static final byte YES = 1;
    }

    public static final class SUMMARY_TYPE {
        public static final int DAILY = 0;
        public static final int STAGE = 1;
    }

    public static final class ISDELETE {
        public static final byte YES = 1;
        public static final byte NO = 0;
    }

    public static final class ISMASTER {
        public static final short YES = 1;// 是主治医生
        public static final short NO = 0;// 不是主治医生
    }

    public static final class ORDERTYPE {
        public static final Short ZXDH = 1;// 咨询类
        public static final Short LYDH = 2;// 旅游类
        public static final Short BJDH = 3;// 保健专家类
        public static final Short COINCHONGZHI = 4;// 金币充值
        public static final Short WALLETCHONGZHI = 5;// 钱包充值
        public static final Short CASHPICI = 6;// 退款提现批次号

        public static final Byte UNPAY = 0;
        public static final Byte PAY = 1;// 执行中
        public static final Byte TUIKUANDONE = 2;// 退款完成
        public static final Byte TUIKUANZHONG = 3;// 退款中
        public static final Byte GUOQI = 4;// 已过期
    }

    public static final class PRICE {
        public static final Byte ZIXUN = 0;
        public static final Byte TRAVEL = 1;
        public static final Byte BJMS = 2;//
        public static final Byte OLDER = 0;
        public static final Byte NORMAL = 1;
        public static final Byte KIDS = 2;
    }

    public static final class DDH {
        public static final String ZXDHH = "00000000000";// 11位咨询
        public static final String LYDHH = "01000000000";// 旅游
        public static final String BJDHH = "02000000000";// 保健专家
        public static final String COIN_RECHARGE_DHH = "03000000000";// 金币充值、金币消费订单号
        public static final String WALLET_RECHARGE_DHH = "04000000000";// 钱包充值、钱包消费订单号
        public static final String CASHPICI = "05000000000";// 退款提现批次号

    }

    public static final class TRAVEL {
        public static final Byte DOING = 0;// 执行中
        public static final Byte OUTDATE = 1;// 过期
    }

    public static final class Chat {
        public static final byte HUIFU = 1;
        public static final byte WEIHUIFU = 0;
        public static final byte READ = 2;
        public static final byte UNREAD = 3;
        public static final byte ASK = 0;
        public static final byte REPLY = 1;
    }

    public static final class MONITOR_DATA_REF_VALUE_AGE_SEX_AFFECT {
        public static final int NONE = 0;
        public static final int AGE = 1;
        public static final int SEX = 2;
        public static final int BOTH = 4;
    }

    public static final class MONITOR_DATA_CODE {
        public static final String TW = "TW";
        public static final String BLOOD_PRESURE = "BP";
        public static final String BLOOD_PRESURE_SZY = "SZY";
        public static final String BLOOD_PRESURE_SSY = "SSY";
        public static final String XL = "XL";
        public static final String XT = "XT";
        public static final String KFXT = "KFXT";
        public static final String ZCQXT = "ZCQXT";
        public static final String ZCHXT = "ZCHXT";
        public static final String WUCQXT = "WUCQXT";
        public static final String WUCHXT = "WUCHXT";
        public static final String WANCQXT = "WANCQXT";
        public static final String WANCHXT = "WANCHXT";
        public static final String SQXT = "SQXT";
    }

    public static final class MESSAGE_SEND_WAY {
        // 短信
        public static final int SHORT_MSG = 0;
        // 站内信
        public static final int STATION_MESSAGE = 1;
        // 站内信
        public static final int MAIL = 2;
    }

    public static final class USER_TYPE {
        // 患者
        public static final int PATIENT = 0;
        // 专家
        public static final int EXPERT = 1;
        // 驿站人员
        public static final int STAGE_MEMBER = 3;
        // 客服
        public static final int CUSTOMER_SERVICE_STAFF = 2;

    }

    public static final class EXPERT_TYPE {
        // 普通专家
        public static final byte NORMAL = 0;
        // 高级专家
        public static final byte SUPERIOR = 1;
    }

    public static final class RESERVE_TYPE {
        // 视频
        public static final int VIDEO = 1;
        // 电话
        public static final int PHONE = 2;
        // 门诊
        public static final int MENZHEN = 3;
        // 数组
        public static final String[] zxlx = { "视频咨询", "电话咨询", "门诊咨询" };
    }

    public static final class RESERVE_TIME {
        // 上午
        public static final int FORENOON = 1;
        // 下午
        public static final int AFTERNOON = 96;
    }

    public static final class MSG_TYPE {
        // 通知
        public static final int INFORMATION = 1;
        // 待办
        public static final int TODO = 2;

    }

    public static final class ORGINIZATION_TYPE {
        // 公司
        public static final int COMPANY = 1;
        // 驿站人员
        public static final int HOSPITAL = 2;
        // 客服
        public static final int STAGE = 3;

        public static final int CUSTOMER_SERVICE = 4;

    }

    public static final class MESSAGE_READ_STATE {
        // 未读
        public static final int UNREAD = 0;
        // 已读
        public static final int READ = 1;
    }

    public static final class FROZEN_STATE {
        // 未读
        public static final int NONE = 0;
        // 已读
        public static final int FROZEN = 1;
    }

    public static final class HEALTHCARE_STATE {
        // 待执行
        public static final Short ZHIXINGZHONG = 0;
        // 已完成
        public static final Short YIWANCHENG = 1;
        // 拒绝
        public static final Short JUJUE = 2;
        // 解除
        public static final Short JIECHU = 3;
        // 待支付
        public static final Short WAITPAY = 4;
        // 已取消
        public static final Short YIQUXIAO = 5;
        // 过期
        public static final Short GUOQI = 6;

        // 操作类型，0增加，1修改
        public static final Short ZENGJIA = 0;
        // 操作类型，0增加，1修改
        public static final Short XIUGAI = 1;
    }

    // 科室级别
    public static final short KE_SHI_JI_BIE = 2;
    // 专家出诊时间表的status
    public static final short ZHUAN_JIA_STATUS = 1;

    // String[] zxzt={}

    public static final class CP_SATAUS {
        // 注册完
        public static final Short WC_ZHUCE = 0;
        // 填完信息
        public static final Short WC_XINXI = 1;
        // 完成测评
        public static final Short WC_CEPING = 2;
        // 选完专家
        public static final Short WC_XUAN_ZHUANJIA = 3;
        // 专家测评完
        public static final Short WC_ZHUANJIA_CEPING = 4;
        // 方案制定完
        public static final Short WC_FANGAN_ZHIDING = 5;
        // 方案开始执行
        public static final Short START_PLAN = 6;
    }

    public static final class SIGN {
        public static final Byte sign = 1;
        public static final Byte unsign = 0;
    }

    public static final class CoinOrgType {
        public static final Byte add = 1;
        public static final Byte cut = 0;
    }

    public static final class CoinpayType {
        public static final Byte unpay = 0;
        public static final Byte pay = 1;
        public static final Byte yiquxiao = 2;
    }

    public static final class CoinConsumeType {// 消费类型
        public static final Byte sign = 0;
        public static final Byte habbit_test = 1;// 完成测评
        public static final Byte invite_patient = 2;// 患者邀请码奖励
        public static final Byte invite_expert = 3;// 医生邀请码奖励
        public static final Byte add_expert = 4;// 添加秘书
        public static final Byte add_myexpert = 5;// 客户添加自己为秘书
        public static final Byte add_coin_rmb = 6;//人民币充值积分
        public static final Byte body_test = 9;// 
        public static final Byte base_info = 8;// 
    }

    public static final class pushState {
        public static final Byte doing = 0;
        public static final Byte done = 1;// 完成
        public static final Byte yiquxiao = 2;// 完成
    }

    public static final class AccountType {
        public static final Byte system = 0;// 系统账户
        public static final Byte personal = 1;// 个人账户
    }

    public static final class AccountOperateState {
        public static final Byte doing = 0;// 执行中
        public static final Byte done = 1;// 已完成
        public static final Byte failed = 2;// 已失败
    }

    public static final class AccountOperateType {
        public static final Byte chongzhi = 0;
        public static final Byte tixian = 1;
        public static final Byte goumai = 2;
        public static final Byte tuikuan = 3;
        public static final Byte fail_backcash = 4;// 退款失败后的返现
    }

    public static final class PriceProgramType {
        public static final Byte travel = 0;
        public static final Byte coin = 1;
        public static final Byte cointocash = 5;
        public static final Byte ios = 6;
        public static final Byte android = 7;
    }

    public static final class EXPERT_STATUS {
        public static final Short PENGDING_AUDIT = 0;// 待审核
        public static final Short AUDIT_THROUGH = 1;// 审核通过
        public static final Short NOT_AUDIT_THROUGH = 2;// 审核不通过
    }

    public static final class PLAN_TYPE {
        public static final String DIARY = "0";// 日记
        public static final String TEMPLATE = "1";// 模板
        public static final String COMMONPLAN = "2";// 系统 模板
    }

    public static final class PUSH_NEWS_TYPE {
        public static final Byte ADD = 0;// 添加
        public static final Byte RENEW = 2;// 续约
    }
}
