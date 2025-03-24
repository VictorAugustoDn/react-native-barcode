require 'json'

package_json = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name                = 'RNLBarCode'
  s.version             = '1.3.1'
  s.author              = { 'yyyyu' => 'g592842897@gmail.com' }
  s.license             = 'MIT'
  s.homepage            = 'https://github.com/yyyyu/react-native-barcode'
  s.source              = { :git => 'https://github.com/yyyyu/react-native-barcode.git', :tag => "v#0.0.0-development" }
  s.summary             = 'react native barcode scanner and decoder.'

  s.platform            = :ios, '8.0'
  s.source_files        = 'ios/*.{h,m}'
  s.dependency 'React'
  s.dependency 'React-Core'
  s.dependency 'ReactCommon'
end
