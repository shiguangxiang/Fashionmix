package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Question extends BaseBean {


    private boolean has_best_answer;
    private Comment best_answer;

    public boolean isHas_best_answer() {
        return has_best_answer;
    }

    public void setHas_best_answer(boolean has_best_answer) {
        this.has_best_answer = has_best_answer;
    }

    public Comment getBest_answer() {
        return best_answer;
    }

    public void setBest_answer(Comment best_answer) {
        this.best_answer = best_answer;
    }

}

