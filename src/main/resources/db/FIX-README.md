# Database Fix Scripts

This directory contains scripts to fix the issues with the sample data in the database.

## Problem Description

When running the sample-data.sql script, some INSERT statements for the BODE table fail with the error:
```
String or binary data would be truncated in table 'THI_TRAC_NGHIEM.dbo.BODE', column 'B'.
```

This happens because some text values exceed the defined column size of NVARCHAR(50) for columns A, B, C, and D in the BODE table.

## Available Fix Options

There are two fix scripts available, depending on your preference:

### Option 1: fix-sample-data.sql

This script retains the original database schema but shortens the problematic text values to fit within the column size constraints.

**Use this option if:**
- You want to keep the current database schema unchanged
- You're okay with shortened text in some questions
- You want a minimal change approach

**How to use:**
```sql
-- In SQL Server Management Studio or similar tool
USE THI_TRAC_NGHIEM;
GO
-- Run the fix script
:r src\main\resources\db\fix-sample-data.sql
```

### Option 2: alter-schema.sql

This script modifies the database schema by increasing the column sizes in the BODE table to accommodate the longer text values, and then reinserts the problematic records with their full text.

**Use this option if:**
- You want to keep the original, complete text for all questions
- You're okay with modifying the database schema
- You prefer having more space for future question text

**How to use:**
```sql
-- In SQL Server Management Studio or similar tool
USE THI_TRAC_NGHIEM;
GO
-- Run the alter schema script
:r src\main\resources\db\alter-schema.sql
```

## Recommendation

For a production environment, Option 2 (alter-schema.sql) is generally recommended because:
1. It preserves the full meaning of all question texts
2. It allows for more flexibility in the future when adding new questions
3. Modern databases can easily handle the increased column sizes without significant performance impact

However, if strict adherence to the original schema is required, Option 1 (fix-sample-data.sql) will work too.