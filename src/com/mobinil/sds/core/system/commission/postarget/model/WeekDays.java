/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.model;

/**
 *
 * @author mabdelaal
 */
public class WeekDays {
   public WeekDays(int week, int month, int start, int end) {
            this.week = week;
            this.month = month;
            this.start = start;
            this.end = end;
        }

        

        public WeekDays() {
        }
        private int week, month, start,end;

        public int getWeek() {
            return week;
        }

        @Override
        public String toString() {
            return "WeekDays{" + "week=" + week + ", month=" + month + ", start=" + start + ", end=" + end + '}';
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
 
}
