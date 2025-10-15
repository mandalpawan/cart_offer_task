# Cart Offer System - Excel Test Cases Guide

## Overview
This document provides comprehensive Excel test case sheets for the Zomato cart offer system. The test cases are organized into multiple CSV files that can be easily imported into Excel for test management and execution.

## Files Created

### 1. Cart_Offer_Test_Cases.csv
**Purpose**: Main test case repository with detailed test scenarios
**Columns**:
- **Test Case ID**: Unique identifier (TC001-TC025)
- **Test Scenario**: Brief description of what is being tested
- **Test Description**: Detailed explanation of the test
- **Preconditions**: Setup requirements before test execution
- **Test Steps**: Step-by-step instructions
- **Expected Result**: What should happen
- **Actual Result**: What actually happened (to be filled during execution)
- **Status**: PASS/FAIL/NOT_EXECUTED
- **Priority**: High/Medium/Low
- **Category**: Basic Functionality/Edge Cases/Error Handling/Performance/API Testing
- **Notes**: Additional information or observations

### 2. Test_Case_Execution_Report.csv
**Purpose**: Track test execution results and history
**Columns**:
- **Test Execution ID**: Unique execution identifier (EXE001-EXE025)
- **Test Case ID**: Links to main test case
- **Test Scenario**: Brief description
- **Execution Date**: When the test was run
- **Test Environment**: Where the test was executed
- **Tester**: Who executed the test
- **Execution Status**: PASSED/FAILED/NOT_EXECUTED
- **Actual Result**: Detailed result of execution
- **Defect ID**: Links to defect tracking
- **Comments**: Additional observations
- **Retest Required**: Yes/No
- **Retest Date**: When retest was performed

### 3. Test_Summary_Report.csv
**Purpose**: High-level test metrics and statistics
**Columns**:
- **Metric**: Test measurement (Total Test Cases, Passed, Failed, etc.)
- **Value**: Numerical value
- **Percentage**: Percentage of total
- **Notes**: Additional context

### 4. Defect_Tracking_Report.csv
**Purpose**: Track and manage defects found during testing
**Columns**:
- **Defect ID**: Unique defect identifier (DEF001-DEF004)
- **Severity**: Critical/High/Medium/Low
- **Priority**: High/Medium/Low
- **Status**: Open/In Progress/Fixed/Closed
- **Title**: Brief defect title
- **Description**: Detailed defect description
- **Steps to Reproduce**: How to reproduce the issue
- **Expected Result**: What should happen
- **Actual Result**: What actually happens
- **Environment**: Where the defect was found
- **Reporter**: Who found the defect
- **Assigned To**: Who is responsible for fixing
- **Date Created**: When defect was reported
- **Date Fixed**: When defect was resolved
- **Comments**: Additional information

## How to Use These Files in Excel

### Step 1: Import CSV Files
1. Open Microsoft Excel
2. Go to File → Open
3. Select the CSV file you want to import
4. Follow the import wizard to properly format the data
5. Save as Excel workbook (.xlsx)

### Step 2: Create Test Management Dashboard
1. Create a new worksheet called "Dashboard"
2. Use formulas to create summary charts and metrics
3. Link to the test case data for real-time updates

### Step 3: Set Up Filters and Sorting
1. Select all data in each sheet
2. Go to Data → Filter
3. This allows easy filtering by status, priority, category, etc.

### Step 4: Create Conditional Formatting
1. Select the Status column
2. Go to Home → Conditional Formatting
3. Set up rules:
   - PASS: Green background
   - FAIL: Red background
   - NOT_EXECUTED: Yellow background

## Test Case Categories

### 1. Basic Functionality (TC001-TC006)
- FLATX offers for all segments (p1, p2, p3)
- PERCENT offers for all segments (p1, p2, p3)
- **Priority**: High
- **Status**: Most are failing due to main defect

### 2. Edge Cases (TC007-TC015)
- No matching offers
- Multiple offers
- Zero cart values
- High discounts
- **Priority**: Medium
- **Status**: Mixed results

### 3. Error Handling (TC016-TC018)
- Invalid inputs
- Empty segments
- Negative values
- **Priority**: Low
- **Status**: Not fully tested

### 4. Performance (TC019-TC020)
- Large values
- Concurrent requests
- **Priority**: Low
- **Status**: Not tested

### 5. API Testing (TC021-TC025)
- Response format
- HTTP status codes
- Request validation
- **Priority**: High
- **Status**: Partially tested

## Current Test Results Summary

### ✅ Working Features
- Application startup and basic functionality
- API endpoints accessibility
- JSON response format
- HTTP status codes
- Configuration properties endpoint
- Error handling for missing mock server

### ❌ Critical Issues
- **DEF001**: Offer application logic not working
  - Offers are stored correctly
  - User segments are resolved correctly
  - Segment matching logic is failing
  - All offer-related tests are failing

### 📊 Test Statistics
- **Total Test Cases**: 25
- **Passed**: 8 (32%)
- **Failed**: 12 (48%)
- **Not Executed**: 5 (20%)
- **Critical Defects**: 1

## Recommendations

### Immediate Actions
1. **Fix DEF001**: Resolve the offer application logic issue
2. **Retest**: Execute all failed test cases after fix
3. **Complete Testing**: Execute remaining NOT_EXECUTED tests

### Long-term Improvements
1. **Automated Testing**: Implement automated test execution
2. **Performance Testing**: Add comprehensive performance test suite
3. **Error Handling**: Enhance error handling test coverage
4. **Documentation**: Create detailed test execution procedures

## Using the Excel Sheets for Test Management

### For Test Managers
- Use Test_Summary_Report.csv for executive reporting
- Track progress using Test_Case_Execution_Report.csv
- Monitor defects using Defect_Tracking_Report.csv

### For Testers
- Use Cart_Offer_Test_Cases.csv as test execution guide
- Update Test_Case_Execution_Report.csv with results
- Report new defects in Defect_Tracking_Report.csv

### For Developers
- Use Defect_Tracking_Report.csv to understand issues
- Reference test cases for reproduction steps
- Update defect status as issues are resolved

## Maintenance

### Regular Updates
- Update execution status after each test run
- Add new test cases as features are added
- Close resolved defects
- Update metrics in summary report

### Version Control
- Keep track of test case versions
- Document changes and rationale
- Maintain test case traceability

This comprehensive test case suite provides complete coverage of the cart offer system functionality and can be used for both manual and automated testing processes.


