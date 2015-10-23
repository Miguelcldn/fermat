package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 09-10-2015.
 */
public interface Negotiation {
    UUID getNegotiationId();
    long getStartDate();
    NegotiationStatus getStatus();
    void setStatus(NegotiationStatus status);

    Collection<Clause> getClauses();

    Clause addNewClause(ClauseType type, String value);
    Clause modifyClause(Clause clause, String value);
    Clause modifyClauseStatus(Clause clause, ClauseStatus status);

    ClauseType getNextClauseType();
}