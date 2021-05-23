package com.home.design.builder;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 9:52
 * @Description: 建造者模式
 */
public class Phone {
    private String cpu;
    private String screen;
    private String memory;
    private String mainBoard;

    private Phone(Builder builder) {
        cpu = builder.cpu;
        screen = builder.screen;
        memory = builder.memory;
        mainBoard = builder.mainBoard;
    }

    public static final class Builder{
        private String cpu;
        private String screen;
        private String memory;
        private String mainBoard;

        public Builder() {
        }

        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder screen(String screen){
            this.screen = screen;
            return this;
        }

        public Builder mainBoard(String mainBoard){
            this.mainBoard = mainBoard;
            return this;
        }

        public Builder memory(String memory){
            this.memory = memory;
            return this;
        }

        public Phone build() {
            return new Phone(this);
        }
    }

    @Override
    public String toString() {
        return "Phone{" +
                "cpu='" + cpu + '\'' +
                ", screen='" + screen + '\'' +
                ", memory='" + memory + '\'' +
                ", mainBoard='" + mainBoard + '\'' +
                '}';
    }
}
