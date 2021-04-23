import 'dart:async';

import 'package:flutter/services.dart';

class HealthAndroid {
  static const MethodChannel _channel = const MethodChannel('health_android');

  static Future<bool> get hasPermission async {
    final bool result = await _channel.invokeMethod('hasPermission');
    return result;
  }

  static Future<String> get requestPermission async {
    final String result = await _channel.invokeMethod('requestPermission');
    return result;
  }
}
