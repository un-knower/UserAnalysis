/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.tracker.api.thrift.search;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchStatsResult implements org.apache.thrift.TBase<SearchStatsResult, SearchStatsResult._Fields>, java.io.Serializable, Cloneable, Comparable<SearchStatsResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SearchStatsResult");

  private static final org.apache.thrift.protocol.TField STATS_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("statsList", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField TOTAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("totalCount", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SearchStatsResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SearchStatsResultTupleSchemeFactory());
  }

  public List<SearchStats> statsList; // required
  public long totalCount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    STATS_LIST((short)1, "statsList"),
    TOTAL_COUNT((short)2, "totalCount");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // STATS_LIST
          return STATS_LIST;
        case 2: // TOTAL_COUNT
          return TOTAL_COUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TOTALCOUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STATS_LIST, new org.apache.thrift.meta_data.FieldMetaData("statsList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SearchStats.class))));
    tmpMap.put(_Fields.TOTAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("totalCount", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SearchStatsResult.class, metaDataMap);
  }

  public SearchStatsResult() {
  }

  public SearchStatsResult(
    List<SearchStats> statsList,
    long totalCount)
  {
    this();
    this.statsList = statsList;
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SearchStatsResult(SearchStatsResult other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetStatsList()) {
      List<SearchStats> __this__statsList = new ArrayList<SearchStats>(other.statsList.size());
      for (SearchStats other_element : other.statsList) {
        __this__statsList.add(new SearchStats(other_element));
      }
      this.statsList = __this__statsList;
    }
    this.totalCount = other.totalCount;
  }

  public SearchStatsResult deepCopy() {
    return new SearchStatsResult(this);
  }

  @Override
  public void clear() {
    this.statsList = null;
    setTotalCountIsSet(false);
    this.totalCount = 0;
  }

  public int getStatsListSize() {
    return (this.statsList == null) ? 0 : this.statsList.size();
  }

  public java.util.Iterator<SearchStats> getStatsListIterator() {
    return (this.statsList == null) ? null : this.statsList.iterator();
  }

  public void addToStatsList(SearchStats elem) {
    if (this.statsList == null) {
      this.statsList = new ArrayList<SearchStats>();
    }
    this.statsList.add(elem);
  }

  public List<SearchStats> getStatsList() {
    return this.statsList;
  }

  public SearchStatsResult setStatsList(List<SearchStats> statsList) {
    this.statsList = statsList;
    return this;
  }

  public void unsetStatsList() {
    this.statsList = null;
  }

  /** Returns true if field statsList is set (has been assigned a value) and false otherwise */
  public boolean isSetStatsList() {
    return this.statsList != null;
  }

  public void setStatsListIsSet(boolean value) {
    if (!value) {
      this.statsList = null;
    }
  }

  public long getTotalCount() {
    return this.totalCount;
  }

  public SearchStatsResult setTotalCount(long totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    return this;
  }

  public void unsetTotalCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STATS_LIST:
      if (value == null) {
        unsetStatsList();
      } else {
        setStatsList((List<SearchStats>)value);
      }
      break;

    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STATS_LIST:
      return getStatsList();

    case TOTAL_COUNT:
      return Long.valueOf(getTotalCount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STATS_LIST:
      return isSetStatsList();
    case TOTAL_COUNT:
      return isSetTotalCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SearchStatsResult)
      return this.equals((SearchStatsResult)that);
    return false;
  }

  public boolean equals(SearchStatsResult that) {
    if (that == null)
      return false;

    boolean this_present_statsList = true && this.isSetStatsList();
    boolean that_present_statsList = true && that.isSetStatsList();
    if (this_present_statsList || that_present_statsList) {
      if (!(this_present_statsList && that_present_statsList))
        return false;
      if (!this.statsList.equals(that.statsList))
        return false;
    }

    boolean this_present_totalCount = true;
    boolean that_present_totalCount = true;
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(SearchStatsResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetStatsList()).compareTo(other.isSetStatsList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatsList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statsList, other.statsList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(other.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalCount, other.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SearchStatsResult(");
    boolean first = true;

    sb.append("statsList:");
    if (this.statsList == null) {
      sb.append("null");
    } else {
      sb.append(this.statsList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalCount:");
    sb.append(this.totalCount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (statsList == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'statsList' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'totalCount' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SearchStatsResultStandardSchemeFactory implements SchemeFactory {
    public SearchStatsResultStandardScheme getScheme() {
      return new SearchStatsResultStandardScheme();
    }
  }

  private static class SearchStatsResultStandardScheme extends StandardScheme<SearchStatsResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SearchStatsResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STATS_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.statsList = new ArrayList<SearchStats>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  SearchStats _elem2;
                  _elem2 = new SearchStats();
                  _elem2.read(iprot);
                  struct.statsList.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setStatsListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOTAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.totalCount = iprot.readI64();
              struct.setTotalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetTotalCount()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalCount' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SearchStatsResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.statsList != null) {
        oprot.writeFieldBegin(STATS_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.statsList.size()));
          for (SearchStats _iter3 : struct.statsList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
      oprot.writeI64(struct.totalCount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SearchStatsResultTupleSchemeFactory implements SchemeFactory {
    public SearchStatsResultTupleScheme getScheme() {
      return new SearchStatsResultTupleScheme();
    }
  }

  private static class SearchStatsResultTupleScheme extends TupleScheme<SearchStatsResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SearchStatsResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.statsList.size());
        for (SearchStats _iter4 : struct.statsList)
        {
          _iter4.write(oprot);
        }
      }
      oprot.writeI64(struct.totalCount);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SearchStatsResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.statsList = new ArrayList<SearchStats>(_list5.size);
        for (int _i6 = 0; _i6 < _list5.size; ++_i6)
        {
          SearchStats _elem7;
          _elem7 = new SearchStats();
          _elem7.read(iprot);
          struct.statsList.add(_elem7);
        }
      }
      struct.setStatsListIsSet(true);
      struct.totalCount = iprot.readI64();
      struct.setTotalCountIsSet(true);
    }
  }

}

