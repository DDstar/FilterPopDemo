package org.demo.filterpopview;

/**
 * Created by GuoJi on 2017/3/19.
 */

public class DataBean {
    public static class DataLeft {
        public DataLeft(String name, boolean isSelected, int rightSelectedPos) {
            this.name = name;
            this.isSelected = isSelected;
            this.rightSelectedPos = rightSelectedPos;
        }

        String name;
        boolean isSelected;
        int rightSelectedPos;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getRightSelectedPos() {
            return rightSelectedPos;
        }

        public void setRightSelectedPos(int rightSelectedPos) {
            this.rightSelectedPos = rightSelectedPos;
        }
    }

    public static class DataRight {
        public DataRight(String name, boolean isSelected) {
            this.name = name;
            this.isSelected = isSelected;
        }

        String name;
        boolean isSelected;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
