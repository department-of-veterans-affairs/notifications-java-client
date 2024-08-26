# Java client documentation

This documentation is for developers interested in using the VANotify Java client to send emails, text messages or letters.

## Set up the client

### Install the client

The `vanotify-java-client` artifacts are stored in github packages.

Go to the [VANotify Java client maven packages on github](https://maven.pkg.github.com/department-of-veterans-affairs/notifications-java-client) [external link]:

1. Select the most recent version.
1. Copy the dependency configuration snippet for your build tool.

Refer to the [client changelog](https://github.com/department-of-veterans-affairs/notifications-java-client/blob/main/CHANGELOG.md) for the version number and the latest updates.

### Create a new instance of the client

Add this code to your application:

```java
import gov.va.vanotify.NotificationClient;
NotificationClient client = new NotificationClient(apiKey);
```

To get an API key, [sign in to VANotify](https://notifications.va.gov) and go to the __API integration__ page. You can find more information in the [API keys](#api-keys) section of this documentation.

If you use a proxy you can pass it into the NotificationClient constructor.

```java
import gov.va.vanotify.NotificationClient;
NotificationClient client = new NotificationClient(apiKey, proxy);
```

## Send a message

You can use VANotify to send text messages and emails.

### Send a text message

#### Method

```java
SendSmsResponse response = client.sendSms(
    templateId,
    phoneNumber,
    personalisation,
    reference,
    smsSenderId,
    billingCode
);
```

#### Arguments

##### templateId (required)

To find the template ID:

1. [Sign in to VANotify](https://notifications.va.gov).
1. Go to the __Templates__ page and select the relevant template.
1. Select __Copy template ID to clipboard__.

For example:

```
String templateId="f33517ff-2a88-4f6e-b855-c550268ce08a";
```

##### phoneNumber (required)

The phone number of the recipient of the text message. This number can be a US or international number.

```
String phoneNumber="+16132532222";
```

##### personalisation (required)

If a template has placeholder fields for personalised information such as name or reference number, you must provide their values in a map. For example:

```java
Map<String, Object> personalisation = new HashMap<>();
personalisation.put("first_name", "Amala");
personalisation.put("application_date", "2018-01-01");
personalisation.put("list", listOfItems); // Will appear as a comma separated list in the message
```

If a template does not have any placeholder fields for personalised information, you must pass in an empty map or `null`.

##### reference (required)

A unique identifier you create. This reference identifies a single unique notification or a batch of notifications. It must not contain any personal information such as name or postal address. If you do not have a reference, you must pass in an empty string or `null`.

```
String reference='STRING';
```

##### smsSenderId (optional)

A unique identifier of the sender of the text message notification.

To find the text message sender:

1. [Sign in to VANotify](https://notifications.va.gov).

You can then either:

  - copy the sender ID that you want to use and paste it into the method
  - select __Change__ to change the default sender that the service will use, and select __Save__

```
String smsSenderId='8e222534-7f05-4972-86e3-17c5d9f894e2'
```

You can leave out this argument if your service only has one text message sender, or if you want to use the default sender.

##### billingCode (optional)

A string that represents a billing code under which a notification will be reported.
This can be used to group notifications of the same template in billing reports.

```
String billingCode='some-billing-code'
```

You can leave out this argument if you don't need to group notifications with billing codes.

#### Response

If the request to the client is successful, the client returns a `SendSmsResponse`:

```java
UUID notificationId;
Optional<String> reference;
UUID templateId;
int templateVersion;
String templateUri;
String body;
Optional<String> fromNumber;
Optional<String> billingCode;
```

If you are using the [test API key](#test), all your messages come back with a `delivered` status.

All messages sent using the [team and guest list](#team-and-guest-list) or [live](#live) keys appear on your dashboard.

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|Message|How to fix|
|:---|:---|:---|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Can't send to this recipient using a team-only API key"`<br>`}]`|Use the correct type of [API key](#api-keys)|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Can't send to this recipient when service is in trial mode - see https://notifications.va.gov"`<br>`}]`|Your service cannot send this notification in trial mode|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|
|`429`|`[{`<br>`"error": "RateLimitError",`<br>`"message": "Exceeded rate limit for key type TEAM/TEST/LIVE of 3000 requests per 60 seconds"`<br>`}]`|Refer to [API rate limits](#rate-limits) for more information|
|`429`|`[{`<br>`"error": "TooManyRequestsError",`<br>`"message": "Exceeded send limits (LIMIT NUMBER) for today"`<br>`}]`|Refer to [service limits](#daily-limits) for the limit number|
|`500`|`[{`<br>`"error": "Exception",`<br>`"message": "Internal server error"`<br>`}]`|Notify was unable to process the request, resend your notification|

### Send an email

#### Method

```java
SendEmailResponse response = client.sendEmail(
    templateId,
    emailAddress,
    personalisation,
    reference,
    emailReplyToId,
    billingCode
);
```

#### Arguments


##### templateId (required)

To find the template ID:

1. [Sign in to VANotify](https://notifications.va.gov).
1. Go to the __Templates__ page and select the relevant template.
1. Select __Copy template ID to clipboard__.

For example:

```
String templateId="f33517ff-2a88-4f6e-b855-c550268ce08a";
```

##### emailAddress (required)

The email address of the recipient.

```
String emailAddress='sender@something.com';
```

##### personalisation (required)

If a template has placeholder fields for personalised information such as name or application date, you must provide their values in a map. For example:

```java
Map<String, Object> personalisation = new HashMap<>();
personalisation.put("first_name", "Amala");
personalisation.put("application_date", "2018-01-01");
personalisation.put("list", listOfItems); // Will appear as a bulleted list in the message

```
If a template does not have any placeholder fields for personalised information, you must pass in an empty map or `null`.

##### reference (required)

A unique identifier you create. This reference identifies a single unique notification or a batch of notifications. It must not contain any personal information such as name or postal address. If you do not have a reference, you must pass in an empty string or `null`.

```
String reference='STRING';
```

##### emailReplyToId (optional)

This is an email address specified by you to receive replies from your users. You must add at least one reply-to email address before your service can go live.

To add a reply-to email address:

1. [Sign in to VANotify](https://notifications.va.gov).
1. Go to the __Settings__ page.
1. In the __Email__ section, select __Manage__ on the __Reply-to email addresses__ row.
1. Select __Add reply-to address__.
1. Enter the email address you want to use, and select __Add__.

```
String emailReplyToId='8e222534-7f05-4972-86e3-17c5d9f894e2'
```

You can leave out this argument if your service only has one reply-to email address, or you want to use the default email address.

##### billingCode (optional)

A string that represents a billing code under which a notification will be reported.
This can be used to group notifications of the same template in billing reports.

```
String billingCode='some-billing-code'
```

You can leave out this argument if you don't need to group notifications with billing codes.

#### Response

If the request to the client is successful, the client returns a `SendEmailResponse`:

```java
UUID notificationId;
Optional<String> reference;
UUID templateId;
int templateVersion;
String templateUri;
String body;
String subject;
Optional<String> fromEmail;
Optional<String> billingCode;
```

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|Message|How to fix|
|:--- |:---|:---|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Can't send to this recipient using a team-only API key"`<br>`}]`|Use the correct type of [API key](#api-keys)|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Can't send to this recipient when service is in trial mode - see https://notifications.va.gov/trial-mode"`<br>`}]`|Your service cannot send this notification in [trial mode](https://notifications.va.gov/features/using-notify#trial-mode)|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|
|`429`|`[{`<br>`"error": "RateLimitError",`<br>`"message": "Exceeded rate limit for key type TEAM/TEST/LIVE of 3000 requests per 60 seconds"`<br>`}]`|Refer to [API rate limits](#rate-limits) for more information|
|`429`|`[{`<br>`"error": "TooManyRequestsError",`<br>`"message": "Exceeded send limits (LIMIT NUMBER) for today"`<br>`}]`|Refer to [service limits](#daily-limits) for the limit number|
|`500`|`[{`<br>`"error": "Exception",`<br>`"message": "Internal server error"`<br>`}]`|Notify was unable to process the request, resend your notification|

### Send a file by email

To send a file by email, add a placeholder to the template then upload a file. The placeholder will contain a secure link to download the file.

The file will be available for the recipient to download for 18 months.

The links are unique and unguessable. VANotify cannot access or decrypt your file.

#### Add a placeholder to the template

1. [Sign in to VANotify](https://notifications.va.gov).
1. Go to the __Templates__ page and select the relevant email template.
1. Select __Edit__.
1. Add a placeholder to the email template using double brackets. For example:

"Download your file at: ((link_to_file))"

#### Upload your file

You can upload PDF, CSV, .odt, .txt, .rtf, .xlsx and MS Word Document files. Your file must be smaller than 2MB. [Contact the VANotify team](https://github.com/department-of-veterans-affairs/notification-api) if you need to send other file types.

1. Convert the PDF to a `byte[]`.
1. Pass the `byte[]` to the personalisation argument.
1. Call the [sendEmail method](#send-an-email).

For example:

```java
ClassLoader classLoader = getClass().getClassLoader();
File file = new File(classLoader.getResource("document_to_upload.pdf").getFile());
byte [] fileContents = FileUtils.readFileToByteArray(file);

HashMap<String, Object> personalisation = new HashMap();
personalisation.put("link_to_file", client.prepareUpload(fileContents));
client.sendEmail(templateId,
                 emailAddress,
                 personalisation,
                 reference,
                 emailReplyToId);
```

##### CSV Files

Uploads for CSV files should use the `isCsv` parameter on the `prepareUpload()` function.  For example:

```java
ClassLoader classLoader = getClass().getClassLoader();
File file = new File(classLoader.getResource("document_to_upload.pdf").getFile());
byte [] fileContents = FileUtils.readFileToByteArray(file);

HashMap<String, Object> personalisation = new HashMap();
personalisation.put("link_to_file", client.prepareUpload(fileContents, true));
client.sendEmail(templateId,
                 emailAddress,
                 personalisation,
                 reference,
                 emailReplyToId);
```

#### Error codes

If the request is not successful, the client returns an `HTTPError` containing the relevant error code.

|error.status_code|error.message|How to fix|
|:---|:---|:---|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Can't send to this recipient using a team-only API key"`<br>`}]`|Use the correct type of [API key](#api-keys)|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Can't send to this recipient when service is in trial mode - see https://notifications.va.gov"`<br>`}]`|Your service cannot send this notification in trial mode|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Unsupported file type '(FILE TYPE)'. Supported types are: '(ALLOWED TYPES)'"`<br>`}]`|Wrong file type. You can only upload .pdf, .csv, .txt, .doc, .docx, .xlsx, .rtf or .odt files|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "File did not pass the virus scan"`<br>`}]`|The file contains a virus|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Send files by email has not been set up - add contact details for your service at https://notifications.va.gov"`<br>`}]`|See how to [add contact details to the file download page](#add-contact-details-to-the-file-download-page)|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct type of [API key](#api-keys)|
|`413`|`File is larger than 2MB`|The file is too big. Files must be smaller than 2MB|
|`429`|`[{`<br>`"error": "RateLimitError",`<br>`"message": "Exceeded rate limit for key type TEAM/TEST/LIVE of 3000 requests per 60 seconds"`<br>`}]`|Refer to [API rate limits](#rate-limits) for more information|
|`429`|`[{`<br>`"error": "TooManyRequestsError",`<br>`"message": "Exceeded send limits (LIMIT NUMBER) for today"`<br>`}]`|Refer to [service limits](#daily-limits) for the limit number|
|`500`|`[{`<br>`"error": "Exception",`<br>`"message": "Internal server error"`<br>`}]`|Notify was unable to process the request, resend your notification.|

## Get message status


### Get the status of one message

You can only get the status of messages sent within the retention period. The default retention period is 7 days.

#### Method

```java
Notification notification = client.getNotificationById(notificationId);
```

#### Arguments

##### notificationId (required)

The ID of the notification. To find the notification ID, you can either:

* check the response to the [original notification method call](#response)
* [sign in to VANotify](https://notifications.va.gov) and go to the __API integration__ page

#### Response

If the request to the client is successful, the client returns a `Notification`:

```java
UUID id;
Optional<String> reference;
Optional<String> emailAddress;
Optional<String> phoneNumber;
Optional<String> line1;
Optional<String> line2;
Optional<String> line3;
Optional<String> line4;
Optional<String> line5;
Optional<String> line6;
Optional<String> line7;
Optional<String> postage;
String notificationType;
String status;
UUID templateId;
int templateVersion;
String templateUri;
String body;
Optional<String subject;
DateTime createdAt;
Optional<DateTime> sentAt;
Optional<DateTime> completedAt;
Optional<DateTime> estimatedDelivery;
Optional<String> createdByName;
Optional<String> billingCode;
```

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|Message|How to fix|
|:---|:---|:---|
|`400`|`[{`<br>`"error": "ValidationError",`<br>`"message": "id is not a valid UUID"`<br>`}]`|Check the notification ID|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|
|`404`|`[{`<br>`"error": "NoResultFound",`<br>`"message": "No result found"`<br>`}]`|Check the notification ID|


### Get the status of multiple messages

This API call returns one page of up to 250 messages and statuses. You can get either the most recent messages, or get older messages by specifying a particular notification ID in the [`olderThanId`](#olderthanid-optional) argument.

You can only get the status of messages that are 7 days old or newer.

#### Method

```java
NotificationList notification = client.getNotifications(
    status,
    notificationType,
    reference,
    olderThanId
);
```

To get the most recent messages, you must pass in an empty `olderThanId` argument or `null`.

To get older messages, pass the ID of an older notification into the `olderThanId` argument. This returns the next oldest messages from the specified notification ID.

#### Arguments

You can pass in empty arguments or `null` to ignore these filters.

##### status (optional)

You can filter by each:

* [email status](#email-status-descriptions)
* [text message status](#text-message-status-descriptions)

You can leave out this argument to ignore this filter.

##### notificationType (optional)

You can filter by:

* `email`
* `sms`

##### reference (optional)

A unique identifier you create if necessary. This reference identifies a single unique notification or a batch of notifications. It must not contain any personal information such as name or postal address.

```
String reference='STRING';
```

##### olderThanId (optional)

Input the ID of a notification into this argument. If you use this argument, the client returns the next 250 received notifications older than the given ID.

```
String olderThanId='8e222534-7f05-4972-86e3-17c5d9f894e2'
```

If you pass in an empty argument or `null`, the client returns the most recent 250 notifications.

#### Response

If the request to the client is successful, the client returns a `NotificationList`:

```java
List<Notification> notifications;
String currentPageLink;
Optional<String> nextPageLink;
```

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|Message|How to fix|
|:---|:---|:---|
|`400`|`[{`<br>`"error": "ValidationError",`<br>`"message": "bad status is not one of [created, sending, sent, delivered, pending, failed, technical-failure, temporary-failure, permanent-failure, accepted, received]"`<br>`}]`|Contact the VANotify team|
|`400`|`[{`<br>`"error": "ValidationError",`<br>`"message": "Applet is not one of [sms, email, letter]"`<br>`}]`|Contact the VANotify team|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|

### Email status descriptions

|Status|Description|
|:---|:---|
|#`created`|VANotify has placed the message in a queue, ready to be sent to the provider. It should only remain in this state for a few seconds.|
|#`sending`|VANotify has sent the message to the provider. The provider will try to deliver the message to the recipient for up to 72 hours. VANotify is waiting for delivery information.|
|#`delivered`|The message was successfully delivered.|
|#`permanent-failure`|The provider could not deliver the message because the email address was wrong. You should remove these email addresses from your database.|
|#`temporary-failure`|The provider could not deliver the message. This can happen when the recipient’s inbox is full or their anti-spam filter rejects your email. [Check your content does not look like spam](https://www.gov.uk/service-manual/design/sending-emails-and-text-messages#protect-your-users-from-spam-and-phishing) before you try to send the message again.|
|#`technical-failure`|Your message was not sent because there was a problem between Notify and the provider.<br>You’ll have to try sending your messages again.|

### Text message status descriptions

|Status|Description|
|:---|:---|
|#`created`|VANotify has placed the message in a queue, ready to be sent to the provider. It should only remain in this state for a few seconds.|
|#`sending`|VANotify has sent the message to the provider. The provider will try to deliver the message to the recipient for up to 72 hours. VANotify is waiting for delivery information.|
|#`pending`|VANotify is waiting for more delivery information.<br>VANotify received a callback from the provider but the recipient’s device has not yet responded. Another callback from the provider determines the final status of the text message.|
|#`sent`|The message was sent to an international number. The mobile networks in some countries do not provide any more delivery information. The VANotify website displays this status as 'Sent to an international number'.|
|#`delivered`|The message was successfully delivered.|
|#`permanent-failure`|The provider could not deliver the message. This can happen if the phone number was wrong or if the network operator rejects the message. If you’re sure that these phone numbers are correct, you should [contact VANotify support](https://notifications.va.gov/support). If not, you should remove them from your database. You’ll still be charged for text messages that cannot be delivered.
|#`temporary-failure`|The provider could not deliver the message. This can happen when the recipient’s phone is off, has no signal, or their text message inbox is full. You can try to send the message again. You’ll still be charged for text messages to phones that are not accepting messages.|
|#`technical-failure`|Your message was not sent because there was a problem between Notify and the provider.<br>You’ll have to try sending your messages again. You will not be charged for text messages that are affected by a technical failure.|

## Get a template

### Get a template by ID

#### Method

This returns the latest version of the template.

```java
Template template = client.getTemplateById(templateId);
```

#### Arguments

##### templateId (required)

The ID of the template. [Sign in to VANotify](https://notifications.va.gov) and go to the __Templates__ page to find it.

```
String templateId='f33517ff-2a88-4f6e-b855-c550268ce08a';
```

#### Response

If the request to the client is successful, the client returns a `Template`:

```java
UUID id;
String name;
String templateType;
DateTime createdAt;
Optional<DateTime> updatedAt;
String createdBy;
int version;
String body;
Optional<String> subject;
Optional<Map<String, Object>> personalisation;
Optional<String> letterContactBlock;
```

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|Message|How to fix|
|:---|:---|:---|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|
|`404`|`[{`<br>`"error": "NoResultFound",`<br>`"message": "No Result Found"`<br>`}]`|Check your [template ID](#get-a-template-by-id-arguments-templateid-required)|


### Get a template by ID and version

#### Method

```java
Template template = client.getTemplateVersion(templateId, version);
```

#### Arguments

##### templateId (required)

The ID of the template. [Sign in to VANotify](https://notifications.va.gov) and go to the __Templates__ page to find it.

```
String templateId='f33517ff-2a88-4f6e-b855-c550268ce08a';
```

##### version (required)

The version number of the template.

#### Response

If the request to the client is successful, the client returns a `Template`:

```Java
UUID id;
String name;
String templateType;
DateTime createdAt;
Optional<DateTime> updatedAt;
String createdBy;
int version;
String body;
Optional<String> subject;
Optional<Map<String, Object>> personalisation;
Optional<String> letterContactBlock;
```

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|message|How to fix|
|:---|:---|:---|
|`400`|`[{`<br>`"error": "ValidationError",`<br>`"message": "id is not a valid UUID"`<br>`}]`|Check the notification ID|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|
|`404`|`[{`<br>`"error": "NoResultFound",`<br>`"message": "No Result Found"`<br>`}]`|Check your [template ID](#get-a-template-by-id-and-version-arguments-templateid-required) and [version](#version-required)|


### Get all templates

#### Method

This returns the latest version of all templates.

```java
TemplateList templates = client.getAllTemplates(templateType);
```

#### Arguments

##### templateType (optional)

If you do not use `templateType`, the client returns all templates. Otherwise you can filter by:

- `email`
- `sms`

#### Response

If the request to the client is successful, the client returns a `TemplateList`:

```java
List<Template> templates;
```

If no templates exist for a template type or there no templates for a service, the templates list is empty.

### Generate a preview template

#### Method

This generates a preview version of a template.

```java
TemplatePreview templatePreview = client.getTemplatePreview(
    templateId,
    personalisation
);
```

The parameters in the personalisation argument must match the placeholder fields in the actual template. The API notification client ignores any extra fields in the method.

#### Arguments

##### templateId (required)

The ID of the template. [Sign in to VANotify](https://notifications.va.gov) and go to the __Templates__ page to find it.

```
String templateId='f33517ff-2a88-4f6e-b855-c550268ce08a';
```

##### personalisation (required)

If a template has placeholder fields for personalised information such as name or application date, you must provide their values in a map. For example:

```java
Map<String, Object> personalisation = new HashMap<>();
personalisation.put("first_name", "Amala");
personalisation.put("application_date", "2018-01-01");
```
If a template does not have any placeholder fields for personalised information, you must pass in an empty map or `null`.

#### Response

If the request to the client is successful, the client returns a `TemplatePreview`:

```java
UUID id;
String templateType;
int version;
String body;
Optional<String> subject;
Optional<String> html;
```

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|message|How to fix|
|:---|:---|:---|
|`400`|`[{`<br>`"error": "BadRequestError",`<br>`"message": "Missing personalisation: [PERSONALISATION FIELD]"`<br>`}]`|Check that the personalisation arguments in the method match the placeholder fields in the template|
|`400`|`[{`<br>`"error": "NoResultFound",`<br>`"message": "No result found"`<br>`}]`|Check the [template ID](#generate-a-preview-template-arguments-templateid-required)|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|


## Get received text messages

This API call returns one page of up to 250 received text messages. You can get either the most recent messages, or get older messages by specifying a particular notification ID in the [`olderThanId`](#get-a-page-of-received-text-messages-arguments-olderthanid-optional) argument.

You can only get messages that are 7 days old or newer.

You can also set up [callbacks](#callbacks) for received text messages.

### Enable received text messages

Contact the VANotify team using the [support page](https://notifications.va.gov/support) or [chat to us on Slack](https://dsva.slack.com/messages/C010R6AUPHT) to request a unique number for text message replies.

### Get a page of received text messages

#### Method

```java
ReceivedTextMessageList response = client.getReceivedTextMessages(olderThanId);
```

To get the most recent messages, you must pass in an empty argument or `null`.

To get older messages, pass the ID of an older notification into the `olderThanId` argument. This returns the next oldest messages from the specified notification ID.

#### Arguments

##### olderThanId (optional)

Input the ID of a received text message into this argument. If you use this argument, the client returns the next 250 received text messages older than the given ID.

```
String olderThanId='8e222534-7f05-4972-86e3-17c5d9f894e2'
```

If you pass in an empty argument or `null`, the client returns the most recent 250 text messages.

#### Response

If the request to the client is successful, the client returns a `ReceivedTextMessageList` that returns all received texts.

```java
private List<ReceivedTextMessage> receivedTextMessages;
private String currentPageLink;
private String nextPageLink;
```
The `ReceivedTextMessageList` has the following properties:

```java
private UUID id;
private String notifyNumber;
private String userNumber;
private UUID serviceId;
private String content;
private DateTime createdAt;
```
If the notification specified in the `olderThanId` argument is older than 7 days, the client returns an empty response.

#### Error codes

If the request is not successful, the client returns a `NotificationClientException` containing the relevant error code:

|httpResult|Message|How to fix|
|:---|:---|:---|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Error: Your system clock must be accurate to within 30 seconds"`<br>`}]`|Check your system clock|
|`403`|`[{`<br>`"error": "AuthError",`<br>`"message": "Invalid token: API key not found"`<br>`}]`|Use the correct API key. Refer to [API keys](#api-keys) for more information|
