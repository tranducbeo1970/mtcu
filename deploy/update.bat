rmdir /s release
mkdir release
xcopy /S ..\com.attech.amhs.mtcu\dist\* release\*
xcopy /S resource\* release\*