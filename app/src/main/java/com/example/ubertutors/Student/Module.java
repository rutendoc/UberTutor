package com.example.ubertutors.Student;

public class Module {

    public String moduleName, moduleCode, status;


    public Module() {
        // Constructor required for Firebase Database
    }

    public Module(String moduleName, String moduleCode, String status) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}

