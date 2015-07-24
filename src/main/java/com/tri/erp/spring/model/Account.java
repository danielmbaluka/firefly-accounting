package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by TSI Admin on 9/9/2014.
 */

@Entity
public class Account  implements java.io.Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String code;

    @Size(
            min = 3,
            max = 500,
            message = "The title '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(unique = true)
    private String title;

    @Length(min = 3, max = 3, message = "GL account must be a 3-character code")
    @Column
    private String GLAccount;
 
    @Length(min = 2, max = 2, message = "SL account must be a 2-character code")
    @Column
    private String SLAccount;

    @Length(min = 3, max = 3, message = "Auxiliary account must be a 3-character code")
    @Column
    private String auxiliaryAccount;

    @Range(min = 1, max = 2)
    @Column
    private int normalBalance;

    @Range(min = 0, max = 999)
    @Column
    private int level;

    @Range(min = 0, max = 1)
    @Column
    private int active;

    @Range(min = 0, max = 1)
    @Column
    private int isHeader;

    @Range(min = 0, max = 1)
    @Column
    private int hasSL;

    @Range(min = 0, max = 999, message = "Select a valid account")
    @Column
    private Integer parentAccountId;

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<SegmentAccount> segmentAccounts = new HashSet<>();

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_accountTypeId")
    private AccountType accountType;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_accountGroupId")
    private AccountGroup accountGroup;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Transient
    private Account parentAccount;

    public Account(String code, String title, String GLAccount, String SLAccount, String auxiliaryAccount, int normalBalance, int level, int active, int isHeader, int hasSL, Integer parentAccountId, Set<SegmentAccount> segmentAccounts, AccountType accountType, AccountGroup accountGroup, Account parentAccount) {
        this.code = code;
        this.title = title;
        this.GLAccount = GLAccount;
        this.SLAccount = SLAccount;
        this.auxiliaryAccount = auxiliaryAccount;
        this.normalBalance = normalBalance;
        this.level = level;
        this.active = active;
        this.isHeader = isHeader;
        this.hasSL = hasSL;
        this.parentAccountId = parentAccountId;
        this.segmentAccounts = segmentAccounts;
        this.accountType = accountType;
        this.accountGroup = accountGroup;
        this.parentAccount = parentAccount;
    }

    public Account() {}

    @Override
    public String toString() {
        return this.getTitle();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the normalBalance
     */
    public int getNormalBalance() {
        return normalBalance;
    }

    /**
     * @param normalBalance the normalBalance to set
     */
    public void setNormalBalance(int normalBalance) {
        this.normalBalance = normalBalance;
    }
    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the active
     */
    @JsonProperty("isActive")
    public int isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    @JsonProperty("isActive")
    public void setActive(int active) {
        this.active = active;
    }
    /**
     * @return the GLAccount
     */

    @JsonProperty("glaccount")
    public String getGLAccount() {
        return GLAccount;
    }

    /**
     * @param GLAccount the GLAccount to set
     */

    @JsonProperty("glaccount")
    public void setGLAccount(String GLAccount) {
        this.GLAccount = GLAccount;
    }

    /**
     * @return the SLAccount
     */

    @JsonProperty("slaccount")
    public String getSLAccount() {
        return SLAccount;
    }

    /**
     * @param SLAccount the SLAccount to set
     */

    @JsonProperty("slaccount")
    public void setSLAccount(String SLAccount) {
        this.SLAccount = SLAccount;
    }

    /**
     * @return the auxiliaryAccount
     */
    @JsonProperty("auxAccount")
    public String getAuxiliaryAccount() {
        return auxiliaryAccount;
    }

    /**
     * @param auxiliaryAccount the auxiliaryAccount to set
     */
    @JsonProperty("auxAccount")
    public void setAuxiliaryAccount(String auxiliaryAccount) {
        this.auxiliaryAccount = auxiliaryAccount;
    }

    /**
     * @return the isHeader
     */
    public int isHeader() {
        return getIsHeader();
    }

    /**
     * @param isHeader the isHeader to set
     */

    public void isHeader(int isHeader) {
        this.setIsHeader(isHeader);
    }

    /**
     * @return the hasSL
     */

    @JsonProperty("hasSL")
    public int hasSL() {
        return hasSL;
    }

    /**
     * @param hasSL the hasSL to set
     */
    @JsonProperty("hasSL")
    public void hasSL(int hasSL) {
        this.hasSL = hasSL;
    }


    @JsonProperty("isHeader")
    public int getIsHeader() {
        return isHeader;
    }

    @JsonProperty("isHeader")
    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    public void setAccountGroup(AccountGroup accountGroup) {
        this.accountGroup = accountGroup;
    }

    public Integer getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(Integer parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public Set<SegmentAccount> getSegmentAccounts() {
        return segmentAccounts;
    }

    public void setSegmentAccounts(Set<SegmentAccount> segmentAccounts) {
        this.segmentAccounts = segmentAccounts;
    }

    public Account getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }
}
