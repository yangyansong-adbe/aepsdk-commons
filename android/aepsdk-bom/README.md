# The Maven BOM artifact for Android SDKs

## SDK BOM artifact

We have recently introduced BOM (Bill of Materials) artifacts in our Android SDKs to make it easier for our customers to manage SDK dependencies and ensure compatibility among extensions.

The BOM artifact on [Maven Central](https://central.sonatype.com/artifact/com.adobe.marketing.mobile/sdk-bom/) is available to use now.

## What is BOM

[The BOM artifact](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms), which is a special type of Maven artifact. And Gradle system (used to build Android projects) has begun providing [support](https://docs.gradle.org/current/userguide/platforms.html#sub:bom_import) for BOM artifacts. The BOM artifact can list all our Android extensions along with their respective versions in our case. When you use BOM in your project, you only need to specify the version of the BOM itself, not the versions of individual extensions.

## How to use BOM

The table below illustrates the ways to import our Android SDKs, either with (right side) or without (left side) including the BOM artifact.

<table>
<td>

```groovy
    implementation 'com.adobe.marketing.mobile:core:2.2.3'
    implementation 'com.adobe.marketing.mobile:lifecycle:2.0.3'
    implementation 'com.adobe.marketing.mobile:identity:2.0.3'
    implementation 'com.adobe.marketing.mobile:signal:2.0.1'
    implementation 'com.adobe.marketing.mobile:assurance:2.1.1'
    implementation 'com.adobe.marketing.mobile:userprofile:2.0.0'
    implementation 'com.adobe.marketing.mobile:edge:2.2.0'
    implementation 'com.adobe.marketing.mobile:edgeconsent:2.0.0'
    implementation 'com.adobe.marketing.mobile:edgeidentity:2.0.0'
    implementation 'com.adobe.marketing.mobile:messaging:2.1.4'
    implementation 'com.adobe.marketing.mobile:analytics:2.0.2'
```

</td>
<td>
    =>
</td>
<td>

```groovy
    implementation platform('com.adobe.marketing.mobile:sdk-bom:2.0.1')
    implementation 'com.adobe.marketing.mobile:core'
    implementation 'com.adobe.marketing.mobile:lifecycle'
    implementation 'com.adobe.marketing.mobile:identity'
    implementation 'com.adobe.marketing.mobile:signal'
    implementation 'com.adobe.marketing.mobile:assurance'
    implementation 'com.adobe.marketing.mobile:userprofile'
    implementation 'com.adobe.marketing.mobile:edge'
    implementation 'com.adobe.marketing.mobile:edgeconsent'
    implementation 'com.adobe.marketing.mobile:edgeidentity'
    implementation 'com.adobe.marketing.mobile:messaging'
    implementation 'com.adobe.marketing.mobile:analytics'
```
</td>
</tr>

</table>

## Documentation

For the BOM artifact, we have also revised the SDK documentation to demonstrate the process of [incorporating SDK dependencies into your project](https://developer.adobe.com/client-sdks/documentation/getting-started/get-the-sdk/#1-add-dependencies-to-your-project).

The release notes were also revised to document the extension changes incorporated in the new BOM artifact. You can find an [example](https://developer.adobe.com/client-sdks/documentation/release-notes/#android-bom-201) here.
