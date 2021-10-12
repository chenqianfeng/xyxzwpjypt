package com.city.oa.model;

import lombok.Data;

//用户类
public class Shopper {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String nickName;
    private String phone;
    private String email;
    private String address;
    private Question question;
    private String answer;
    private String password;
    private Integer credit;//默认100
    private Integer power;//默认1  1:普通用户 2：管理员 3：超级管理员

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shopper shopper = (Shopper) o;

        if (name != null ? !name.equals(shopper.name) : shopper.name != null) return false;
        if (gender != null ? !gender.equals(shopper.gender) : shopper.gender != null) return false;
        if (age != null ? !age.equals(shopper.age) : shopper.age != null) return false;
        if (nickName != null ? !nickName.equals(shopper.nickName) : shopper.nickName != null) return false;
        if (phone != null ? !phone.equals(shopper.phone) : shopper.phone != null) return false;
        if (email != null ? !email.equals(shopper.email) : shopper.email != null) return false;
        if (address != null ? !address.equals(shopper.address) : shopper.address != null) return false;
        if (question != null ? !question.equals(shopper.question) : shopper.question != null) return false;
        if (answer != null ? !answer.equals(shopper.answer) : shopper.answer != null) return false;
        if (password != null ? !password.equals(shopper.password) : shopper.password != null) return false;
        if (credit != null ? !credit.equals(shopper.credit) : shopper.credit != null) return false;
        return power != null ? power.equals(shopper.power) : shopper.power == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Shopper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", question=" + question +
                ", answer='" + answer + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", power=" + power +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
