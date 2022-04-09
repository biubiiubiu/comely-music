package com.example.comelymusic.generate.enums;

/**
 * description: 性别
 *
 * @author: zhangtian
 * @since: 2022-04-09 10:57
 */
public enum Gender {
    FEMALE {
        @Override
        public String getGender() {
            return "女";
        }
    },
    MALE {
        @Override
        public String getGender() {
            return "男";
        }
    },
    UNKNOWN {
        @Override
        public String getGender() {
            return "隐藏";
        }
    };

    public abstract String getGender();
}