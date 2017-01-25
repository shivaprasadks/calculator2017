package com.citizen.calculator2017.calclib;

import com.citizen.calculator2017.calclib.HofH.HofHManager;
import java.util.ArrayList;
import com.citizen.calculator2017.utils.NumberFormatter;
import com.citizen.calculator2017.utils.ParametersFromHistory;

public class HistoryManager implements IappConstants {
    static HofHManager hofhmgr;
    static HistoryManager oneNonlyObject;
    private ArrayList<HistoryElements> history;
    private HistoryElements historyElement;
    private int mIndex;

    static {
        oneNonlyObject = null;
    }

    private HistoryManager() {
        this.history = null;
    }

    public static HistoryManager getHistoryManager() {
        if (oneNonlyObject == null) {
            oneNonlyObject = new HistoryManager();
        }
        return oneNonlyObject;
    }

    public void init() {
        this.mIndex = 0;
        this.history = new ArrayList();
        hofhmgr = HofHManager.getHofHManager();
    }

    public void addElement(String operation, double operand) {
        this.historyElement = new HistoryElements();
        this.historyElement.operand = Double.valueOf(operand);
        this.historyElement.operation = operation;
        if (this.history.size() == 0) {
            this.historyElement.comment = "Tap to Comment";
        }
        String prevOperation = BuildConfig.FLAVOR;
        if (getLastElement() != null) {
            prevOperation = getLastElement().operation;
        }
        this.historyElement.prevOperation = prevOperation;
        if (prevOperation.isEmpty()) {
            this.historyElement.comment = "New Calculation";
        }
        this.history.add(this.historyElement);
    }

    public void setPercentagePresent(int p, double val) {
        this.historyElement = (HistoryElements) this.history.get(this.history.size() - 1);
        this.historyElement.percentageMode = p;
        this.historyElement.percentageAmount = val;
        this.historyElement.comment = getCommentString(p, val);
    }

    private String getCommentString(int percentMode, double val) {
        String comment = BuildConfig.FLAVOR;
        if (percentMode != 0) {
            comment = "[" + NumberFormatter.formatDoubleWithoutScientificNotation(val) + "]";
        }
        if (percentMode == 2) {
            return "Tax: " + comment;
        }
        return comment;
    }

    public void setElement(String operation, double operand, int step) {
        if (step == 1) {
            this.history.clear();
        }
        this.historyElement.operand = Double.valueOf(operand);
        this.historyElement.operation = operation;
        this.history.add(step, this.historyElement);
    }

    public void editElement(String prevOperation, double operand, int index, int p) {
        if (index >= 0 && index < this.history.size()) {
            this.historyElement = (HistoryElements) this.history.get(index);
            this.historyElement.prevOperation = prevOperation;
            this.historyElement.operand = Double.valueOf(operand);
            this.historyElement.percentageMode = p;
            if (p != 0) {
                this.historyElement.comment = getCommentString(p, this.historyElement.percentageAmount);
            }
            if (index >= 1) {
                ((HistoryElements) this.history.get(index - 1)).operation = prevOperation;
            }
        }
    }

    public void editElement(String operation, int step) {
        if (step >= 0 && step <= getlength()) {
            this.historyElement = (HistoryElements) this.history.get(step);
            this.historyElement.operation = operation;
        }
    }

    public static void traceLog(String str) {
        System.out.println(str);
    }

    public HistoryElements getElement(int index) {
        if (index >= this.history.size() || index < 0) {
            return null;
        }
        this.historyElement = (HistoryElements) this.history.get(index);
        return this.historyElement;
    }

    public void removelast() {
        this.mIndex = this.history.size() - 1;
        this.history.remove(this.mIndex);
    }

    public void getJsonStr() {
    }

    public void clearRest(int mStep) {
        int size = this.history.size();
        for (int index = mStep; index < size; index++) {
            this.history.remove(index);
        }
    }

    public int getlength() {
        return this.history.size();
    }

    public void editElement(double operand, int i) {
        this.historyElement = (HistoryElements) this.history.get(i);
        this.historyElement.operand = Double.valueOf(operand);
    }

    public ArrayList<HistoryElements> getHistory() {
        return this.history;
    }

    public void setComputedAnswer(Double mAnswer, int i) {
        this.historyElement = (HistoryElements) this.history.get(i);
        this.historyElement.computedAnswer = mAnswer.doubleValue();
        this.historyElement.isAnswer = true;
    }

    public void setComputedAnswerForLast(Double mAnswer) {
        this.historyElement = getLastElement();
        if (this.historyElement != null) {
            this.historyElement.computedAnswer = mAnswer.doubleValue();
            this.historyElement.isAnswer = true;
        }
    }

    public ParametersFromHistory setHistory(ArrayList<HistoryElements> h) {
        this.history = new ArrayList(h);
        ParametersFromHistory p = new ParametersFromHistory();
        int size = h.size();
        HistoryElements he = (HistoryElements) h.get(h.size() - 1);
        p.leftOp = he.operand.doubleValue();
        p.operation = he.operation;
        p.size = size;
        p.answer = he.computedAnswer;
        return p;
    }

    public void savehofh() {
        if (!this.history.isEmpty() && !conatinsInfinity()) {
            hofhmgr.addToHistory(new ArrayList(this.history));
        }
    }

    public boolean conatinsInfinity() {
        for (int i = 0; i < this.history.size(); i++) {
            HistoryElements item = (HistoryElements) this.history.get(i);
            if (item.operand.doubleValue() == Double.POSITIVE_INFINITY || item.computedAnswer == Double.POSITIVE_INFINITY || item.operand.doubleValue() == Double.NEGATIVE_INFINITY || item.computedAnswer == Double.NEGATIVE_INFINITY) {
                return true;
            }
        }
        return false;
    }

    public void clearHistory() {
        this.history.clear();
    }

    public void printlist() {
        if (this.history != null) {
            System.out.println("history size: " + this.history.size());
            for (int i = 0; i < this.history.size(); i++) {
                HistoryElements item = (HistoryElements) this.history.get(i);
                String res = i + ": " + item.prevOperation + "  " + item.operand + "  " + item.comment;
                if (item.percentageMode != 0) {
                    res = res + "%";
                }
                System.out.println(res);
                if (item.isAnswer) {
                    System.out.println("=     " + item.computedAnswer + " " + item.commentOnAnswerNode);
                }
            }
        }
    }

    public HistoryElements getLastElement() {
        if (this.history == null || this.history.size() <= 0) {
            return null;
        }
        return (HistoryElements) this.history.get(this.history.size() - 1);
    }

    public void editLastElement(String operation) {
        this.historyElement = getLastElement();
        if (this.historyElement != null) {
            this.historyElement.operation = operation;
        }
    }

    public int getSteps() {
        if (this.history == null || this.history.size() == 0) {
            return 0;
        }
        return this.history.size() + 1;
    }

    public void editSecondNum() {
        if (getlength() >= 1) {
            ((HistoryElements) this.history.get(1)).operand = Double.valueOf(20.0d);
        }
    }

    public void editFourthNum() {
        if (getlength() > 3) {
            ((HistoryElements) this.history.get(3)).operand = Double.valueOf(20.0d);
        }
    }

    public void setComment(String comment, int i, boolean answer) {
        HistoryElements he = (HistoryElements) this.history.get(i);
        if (answer) {
            he.commentOnAnswerNode = comment;
        } else {
            he.comment = comment;
        }
    }

    public void deleteRow(int historyIndex, boolean isAnswerNode) {
        HistoryElements item = getHistoryElementAt(historyIndex);
        if (item != null) {
            if (isAnswerNode) {
                item.isAnswer = false;
                return;
            }
            HistoryElements next = getHistoryElementAt(historyIndex + 1);
            HistoryElements prev = getHistoryElementAt(historyIndex - 1);
            if (next == null && prev == null) {
                this.history.remove(historyIndex);
                return;
            }
            if (next == null) {
                if (item.isAnswer) {
                    prev.isAnswer = true;
                }
            } else if (prev == null) {
                next.prevOperation = BuildConfig.FLAVOR;
            } else {
                prev.operation = next.prevOperation;
            }
            this.history.remove(historyIndex);
        }
    }

    private HistoryElements getHistoryElementAt(int i) {
        if (i < this.history.size() && i >= 0) {
            return (HistoryElements) this.history.get(i);
        }
        return null;
    }

    public void addItemBelow(HistoryElements itemToAdd, int historyIndex) {
        HistoryElements item = getHistoryElementAt(historyIndex);
        if (item != null) {
            HistoryElements next = getHistoryElementAt(historyIndex + 1);
            HistoryElements prev = getHistoryElementAt(historyIndex - 1);
            if (next != null || prev != null) {
                if (next == null) {
                    item.operation = itemToAdd.prevOperation;
                    itemToAdd.operation = BuildConfig.FLAVOR;
                    itemToAdd.isAnswer = true;
                } else if (prev == null) {
                    item.operation = itemToAdd.prevOperation;
                    itemToAdd.operation = next.prevOperation;
                } else {
                    itemToAdd.operation = next.prevOperation;
                    item.operation = itemToAdd.prevOperation;
                    next.prevOperation = itemToAdd.operation;
                }
                this.history.add(historyIndex + 1, new HistoryElements(itemToAdd));
                printlist();
            }
        }
    }

    public HistoryElements getLast() {
        return (HistoryElements) this.history.get(this.history.size() - 1);
    }

    public void editOperation(String prevOperation, int index) {
        if (index >= 0 && index < this.history.size()) {
            this.historyElement = (HistoryElements) this.history.get(index);
            this.historyElement.prevOperation = prevOperation;
            if (index >= 1) {
                ((HistoryElements) this.history.get(index - 1)).operation = prevOperation;
            }
        }
    }

    public String getComment(int index, boolean forAnswerNode) {
        if (index < 0 || index > this.history.size()) {
            return BuildConfig.FLAVOR;
        }
        return forAnswerNode ? ((HistoryElements) this.history.get(index)).commentOnAnswerNode : ((HistoryElements) this.history.get(index)).comment;
    }

    public void updateCommentAfterEdit(int index) {
        if (index >= 0 && index < this.history.size()) {
            this.historyElement = (HistoryElements) this.history.get(index);
            int p = this.historyElement.percentageMode;
            if (p != 0) {
                this.historyElement.comment = getCommentString(p, this.historyElement.percentageAmount);
            }
        }
    }
}
