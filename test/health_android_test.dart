import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:health_android/health_android.dart';

void main() {
  const MethodChannel channel = MethodChannel('health_android');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    // expect(await HealthAndroid.platformVersion, '42');
  });
}
