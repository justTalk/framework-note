package com.scam.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

public class XMonitorPlugin implements Plugin<Project>{

  @Override
  void apply(Project project) {
    project.beforeEvaluate(new Action<Project>() {
      @Override
      void execute(Project p) {
        System.out.println("XMonitor: " + p.name)
      }
    })
  }
}