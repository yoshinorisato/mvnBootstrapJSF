/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf.enumpkg;

/**
 *
 * @author PC-USER
 */
public enum statusEnum {

    /** 列挙定数の定義 */
    MISYOUNIN("未承認", 1),
    SAKUSEIKAKUNINNZUMI("作成確認済", 2),
    SYOUNINNZUMI("承認済", 3),
    SOUSINZUMI("送信済", 4),
    CANCEL("キャンセル", 5),
    ERROR("エラー", 99);
 
    /** フィールド変数 */
    private String label;
    private int value;
 
    /** コンストラクタ */
    statusEnum(String label, int value) {
        this.label = label;
        this.value = value;
    }
 
    /** 名称取得メソッド */
    public String getLabel() {
        return this.label;
    }
 
    /** 値取得メソッド */
    public int getValue() {
        return this.value;
    }
}
