import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:health_android/health_android.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    _hasPermission();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> _hasPermission() async {
    bool hasPermission;
    // Platform messages may fail, so we use a try/catch PlatformException.
    //
    try {} catch (e) {}
    try {
      hasPermission = await HealthAndroid.hasPermission;
    } catch (e) {
      hasPermission = false;
    }

    if (hasPermission) {
      print("Im gonna try to read data from google fit");
    } else {
      _requestPersmision();
    }
  }

  Future<void> _requestPersmision() async {
    try {
      String request = await HealthAndroid.requestPermission;
      print(request);
    } catch (e) {
      print("Error while trying to request permissions");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Check Google fit permissions'),
          ),
          body: Column(
            children: [
              Center(
                child: Text("Hola"),
              ),
              Center(
                child: ElevatedButton(
                  child: Text("Ask permissions"),
                  onPressed: _hasPermission,
                ),
              )
            ],
          )),
    );
  }
}
